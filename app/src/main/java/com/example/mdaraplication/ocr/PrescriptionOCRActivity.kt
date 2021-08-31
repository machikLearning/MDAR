package com.example.mdaraplication.ocr

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.camera2.*
import android.media.ExifInterface
import android.media.ImageReader
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.util.SparseIntArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mdaraplication.R
import com.example.mdaraplication.patientmain.PatientMainActivity
import com.example.mdaraplication.qrcode.DeviceOrientation
import kotlinx.android.synthetic.main.camera_activity.*
import kotlinx.android.synthetic.main.patient_prescription_ocr.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.ByteBuffer

class PrescriptionOCRActivity : AppCompatActivity() {
    private var REQUEST_CAMERA_CAPTURE = 1;
    private var savedURi = ""
    private lateinit var mPreviewHolder: SurfaceHolder
    private lateinit var deviceOrientation : DeviceOrientation
    private var mHandler : Handler?= null;
    private lateinit var mImageReader: ImageReader
    private lateinit var mCameraDevice: CameraDevice
    private lateinit var mPreviewBuilder: CaptureRequest.Builder
    private lateinit var mSession: CameraCaptureSession
    private val ORIENTATIONS = SparseIntArray()
    private fun initOrientations(){
        ORIENTATIONS.append(ExifInterface.ORIENTATION_NORMAL, 0);
        ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_90, 90);
        ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_180, 180);
        ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_270, 270);
    }
    private var  mDeviceRotation : Int = 0
    private lateinit var mAccelerometer: Sensor
    private lateinit var mMagnetometer: Sensor
    private lateinit var mSensorManager: SensorManager
    private var mHeight: Int = 0
    private var mWidth:Int = 0
    var mCameraId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity)
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // 권한이 없으면 권한을 요청한다.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),98)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),99)
        }else {
            // 권한이 있을 경우에만 layout을 전개한다.
            initOrientations()
            initSize()
            deviceOrientation = DeviceOrientation()
            picture.setOnClickListener{takePicture()}
            initLayout();
        }
    }

    private fun initLayout() {
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        val surfaceView = findViewById<SurfaceView>(R.id.surface_view)
        mPreviewHolder = surfaceView.holder
        mPreviewHolder.addCallback(object : SurfaceHolder.Callback{
            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                initCameraView()
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                mCameraDevice.close()
            }

            override fun surfaceCreated(holder: SurfaceHolder) {
                initCameraView()
            }

        })

    }

    private var mSessionCaptureSession: CameraCaptureSession.CaptureCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : CameraCaptureSession.CaptureCallback(){
        override fun onCaptureFailed(session: CameraCaptureSession, request: CaptureRequest, failure: CaptureFailure) {
            super.onCaptureFailed(session, request, failure)
        }

        override fun onCaptureCompleted(
            session: CameraCaptureSession,
            request: CaptureRequest,
            result: TotalCaptureResult
        ) {
            mSession = session
            super.onCaptureCompleted(session, request, result);
        }

        override fun onCaptureProgressed(
            session: CameraCaptureSession,
            request: CaptureRequest,
            partialResult: CaptureResult
        ) {
            mSession = session
        }
    }

//    private fun unlockFocus() {
//        mPreviewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL)
//        mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
//        mSession.capture(mPreviewBuilder.build(), mSessionCaptureSession, mHandler)
//        mSession.setRepeatingRequest(mPreviewBuilder.build(), mSessionCaptureSession, mHandler)
//    }


    private fun takePicture() {
        try {
            mDeviceRotation = ORIENTATIONS.get(deviceOrientation.getOrientation())
            var captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            captureRequestBuilder.addTarget(mImageReader.surface)
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION,mDeviceRotation)
            var captureRequest = captureRequestBuilder.build()
            mSession.capture(captureRequest, mSessionCaptureSession, mHandler)
            var afterIntent = Intent(this,PatientMainActivity::class.java)
            startActivityForResult(afterIntent,REQUEST_CAMERA_CAPTURE)
        }catch (e:Exception){
            Log.d("takePicture",e.message.toString())
        }
    }

    private fun getRotateBitmap(tempBitmap: Bitmap, degree: Int) : Bitmap? {
        if(tempBitmap == null) return null
        else if(degree == 0) return tempBitmap;
        var matrix : Matrix = Matrix()
        matrix.setRotate(degree.toFloat(),tempBitmap.width.toFloat()/2,tempBitmap.height.toFloat()/2);
        return Bitmap.createBitmap(tempBitmap,0,0,tempBitmap.width,tempBitmap.height,matrix,true)
    }

    private var mOnImagageAvaliableListener : ImageReader.OnImageAvailableListener = @RequiresApi(Build.VERSION_CODES.KITKAT)
    object : ImageReader.OnImageAvailableListener {
        override fun onImageAvailable(reader: ImageReader?) {
            var image = reader?.acquireLatestImage()
            var buffer : ByteBuffer? = image?.planes?.get(0)?.buffer
            var byteArray : ByteArray = ByteArray(buffer!!.remaining())
            buffer.get(byteArray)
            var bitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.size)
            GlobalScope.launch {
                var uri = initImage(bitmap)
                if (uri != null) {
                    savedURi = uri
                }
            }
        }

    }

    private fun initImage(bitmap: Bitmap?) : String? {
        var rotateBitmap : Bitmap? = null;
        try{
            rotateBitmap = getRotateBitmap(bitmap!!,mDeviceRotation)
        }catch (e : java.lang.Exception){
            Log.d("saveFail","fail")
        }
        var uri : String? = insertImage(contentResolver, rotateBitmap,"" + System.currentTimeMillis(),"")
        return uri
    }

    private fun insertImage(contentResolver: ContentResolver?, rotateBitmap: Bitmap?, title: String, description: String) : String? {
        var values : ContentValues = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, title)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        if(contentResolver != null) {
            var uri = contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            var outputStream = uri?.let { contentResolver?.openOutputStream(it) }
            rotateBitmap?.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
            outputStream?.close()
            return uri.toString()
        }
        return null
    }


    private fun openCamera() {
        try{
            val mCameraManager = this.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val characteristics = mCameraManager.getCameraCharacteristics(this.mCameraId.toString())
            val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            val largestPreviewSize = map!!.getOutputSizes(ImageFormat.JPEG)[0]
            val mainHandler = Handler(mainLooper)
            setAspectRatioTextureView(largestPreviewSize.height, largestPreviewSize.width)
            mImageReader = ImageReader.newInstance(
                largestPreviewSize.width,
                largestPreviewSize.height,
                ImageFormat.JPEG,
                7
            )
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
                return
            }
            mImageReader.setOnImageAvailableListener(mOnImagageAvaliableListener, mainHandler)
            mCameraManager.openCamera(mCameraId.toString(), deviceStateCallback, mHandler)
        }catch (e:Exception){
            e.message?.let { Log.e("cameraError", it) }
        }
    }
    private val deviceStateCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object: CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            mCameraDevice = camera
            try {
                takePreview()
            } catch (e: CameraAccessException) {
                e.message?.let { Log.d("cameraError", it) }
            }
        }

        override fun onDisconnected(camera: CameraDevice) {
            mCameraDevice.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            Log.d("cameraOpenError",error.toString())
        }
    }

    private fun takePreview() {
        mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        mPreviewBuilder.addTarget(mPreviewHolder.surface)
        mCameraDevice.createCaptureSession(
            listOf(mPreviewHolder.surface, mImageReader.surface), mSessionPreviewStateCallback, mHandler
        )
    }
    private val mSessionPreviewStateCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : CameraCaptureSession.StateCallback() {
        override fun onConfigured(session: CameraCaptureSession) {
            mSession = session
            try {
                // Key-Value 구조로 설정
                // 오토포커싱이 계속 동작
                mPreviewBuilder.set(
                    CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                )
                //필요할 경우 플래시가 자동으로 켜짐
                mPreviewBuilder.set(
                    CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
                )
                mSession.setRepeatingRequest(mPreviewBuilder.build(), null, mHandler)
            } catch (e: CameraAccessException) {
                Log.d("previewFail", e.printStackTrace().toString())
            }

        }

        override fun onConfigureFailed(session: CameraCaptureSession) {

        }
    }

    private fun setAspectRatioTextureView(height: Int, width: Int) {
        if (width > height) {
            val newWidth = mWidth
            val newHeight = mWidth * width / height
            updateTextureViewSize(newWidth, newHeight)

        } else {
            val newWidth = mWidth
            val newHeight = mWidth * height / width
            updateTextureViewSize(newWidth, newHeight)
        }
    }

    private fun updateTextureViewSize(newWidth: Int, newHeight: Int) {
        surface_view.layoutParams = FrameLayout.LayoutParams(newWidth, newHeight)
    }

    private fun initSize() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            mHeight= windowManager.currentWindowMetrics.bounds.height()
            mWidth = windowManager.currentWindowMetrics.bounds.width()
        }else{
            mWidth = windowManager.defaultDisplay.width
            mHeight = windowManager.defaultDisplay.height
        }
    }

    private fun initCameraView() {
        val handlerThread = HandlerThread("CAMERA2")
        handlerThread.start()
        mHandler = Handler(handlerThread.looper)
        openCamera()
    }

}