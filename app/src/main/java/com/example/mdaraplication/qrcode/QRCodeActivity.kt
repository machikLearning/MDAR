package com.example.mdaraplication.qrcode

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.edb.bpdec.EDBBpDec
import com.example.mdaraplication.R
import com.example.mdaraplication.util.Util
import com.google.zxing.integration.android.IntentIntegrator
import com.ubcare.upharmbarcodelib.cbnuh.Upharm2DDecoder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception
import java.nio.charset.Charset

class QRCodeActivity : AppCompatActivity() {
    private lateinit var edbBpDec : EDBBpDec
    private lateinit var decoder : Upharm2DDecoder
    private lateinit var intentIntegrator : IntentIntegrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qrcode_activity)
        this.intentIntegrator = IntentIntegrator(this)
        this.intentIntegrator.setBeepEnabled(true)
        this.edbBpDec = EDBBpDec(this)
        this.intentIntegrator.initiateScan()
        this.edbBpDec.EDB_CheckUser("edb_cbnuh", "edbpass_cbnuh", "40")
        this.decoder = Upharm2DDecoder(this,"cbnuh00", "cbnuh00")

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if (data != null) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                var parameter : List<String> = checkMedicine(data)
                var builder = AlertDialog.Builder(this)
                if(!parameter.isEmpty()){
                    try {
                        var message = connectServer(parameter)
                        var jsonObject =Util.getJsonObject(message);
                        message = jsonObject.get("message").toString()
                        builder.setMessage(message)
                    }catch(e:Exception){
                        builder.setMessage("네트워크 오류입니다")
                    }
                }else{
                    builder.setMessage("죄송합니다. 지금 스캔하신 처방전의 QR코드는 아직 인식되지 않습니다.\n 처방된 약제를 직접 검색해주세요.");
                }
                builder.show()
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_NETWORK_STATE), 0);
            }
        }
//        if (result != null) {
//            //qrcode 가 없으면
//            if (result.contents == null) {
//                Toast.makeText(this@QRCodeActivity, "취소!", Toast.LENGTH_SHORT).show()
//            } else {
//                //data를 json으로 변환
//                val builder: AlertDialog.Builder = Builder(this)
//                var params: String = this.checkMedicine(data!!.getStringExtra("SCAN_RESULT"))
//                if (params != "") {
//                    params = "id=" + this.id.toString() + params
//                    try {
//                        val qrCodeResponse = QRCodeResponse()
//                        val message: String =
//                            qrCodeResponse.execute(URL.toString() + "/ADRM/patient/checkQRcode", params).get()
//                        builder.setMessage(message)
//                    } catch (e: InterruptedException) {
//                        builder.setMessage("ERROR_CODE : " + Constraints.INTERRUPTED_EXCEPTION)
//                    } catch (e: ExecutionException) {
//                        builder.setMessage("ERROR_CODE : " + Constraints.EXECUTION_EXCEPTION)
//                    }
//                } else {
//                    builder.setMessage("죄송합니다. 지금 스캔하신 처방전의 QR코드는 아직 인식되지 않습니다.\n 처방된 약제를 직접 검색해주세요.")
//                }
//                builder.show()
//                medicinenumber.setText("완료되었습니다")
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data)
//        }
    }

    private fun connectServer(parameter: List<String>): String {
        var result: String = ""
        var retrofit : QRCodeInterface = Util.getRetrofit().create(QRCodeInterface::class.java)
        GlobalScope.launch {
            var message = async{retrofit.checkQRCode(parameter).execute().body().toString()}
            val await = message.await()
            result = await.toString()
        }
        Thread.sleep(1000)
        return result
    }

    private fun checkMedicine(data: Intent): List<String> {
        var str = data?.getStringExtra("SCAN_RESULT").toString()
        var strData = String(str.toByteArray(Charsets.ISO_8859_1), Charset.forName("euc-kr"))
        return  this.decoder.exportDrugcodes(strData)
    }

//    private lateinit var mPreviewHolder: SurfaceHolder
//    private lateinit var deviceOrientation : DeviceOrientation
//    private var mHandler : Handler?= null;
//    private lateinit var mImageReader: ImageReader
//    private lateinit var mCameraDevice: CameraDevice
//    private lateinit var mPreviewBuilder: CaptureRequest.Builder
//    private lateinit var mSession: CameraCaptureSession
//    private val ORIENTATIONS = SparseIntArray()
//    private fun initOrientations(){
//        ORIENTATIONS.append(ExifInterface.ORIENTATION_NORMAL, 0);
//        ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_90, 90);
//        ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_180, 180);
//        ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_270, 270);
//    }
//    private var  mDeviceRotation : Int = 0
//    private lateinit var mAccelerometer: Sensor
//    private lateinit var mMagnetometer: Sensor
//    private lateinit var mSensorManager: SensorManager
//    private var mHeight: Int = 0
//    private var mWidth:Int = 0
//    var mCameraId = 0
//    private lateinit var drawOnMiddle : DrawOnMiddle
//    private lateinit var edbBpDec : EDBBpDec
//    private lateinit var decoder : Upharm2DDecoder
//
//
//    private class DrawOnMiddle(context: Context?, screenCenterX : Int, screenCenterY : Int, radius : Int) : View(context){
//        private var screenCenterX : Int
//        private var screenCenterY : Int
//        private var radius : Int
//        init {
//            this.screenCenterX = screenCenterX
//            this.screenCenterY = screenCenterY
//            this.radius = radius
//        }
//        override fun onDraw(canvas: Canvas?) {
//            super.onDraw(canvas)
//            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
//            paint.setColor(Color.GREEN)
//            paint.setStyle(Paint.Style.STROKE)
//            paint.strokeWidth = 15F
////            canvas!!.drawPaint(paint)
////            paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
//            synchronized(this) {
//                if (canvas != null) {
//                    var rect = Rect()
//                    rect.set(this.screenCenterX, screenCenterY, 2*screenCenterX, 2*screenCenterY )
//                    canvas.drawRect(rect, paint)
//                }
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.camera_activity)
//        this.edbBpDec.EDB_DecodeData(this.toString())
//        this.decoder = Upharm2DDecoder(this,"cbnuh00", "cbnuh00");
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
//            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            // 권한이 없으면 권한을 요청한다.
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),98)
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),99)
//        }else {
//            // 권한이 있을 경우에만 layout을 전개한다.
//            initOrientations()
//            initSize()
//            deviceOrientation = DeviceOrientation()
//            picture.setOnClickListener{takePicture()}
//            initLayout();
//            var left = mWidth/3
//            var top = mHeight/3
//            drawOnMiddle = DrawOnMiddle(this,left,top,1)
//            addContentView(drawOnMiddle, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
//        }
//    }
//
//    private fun initLayout() {
//        getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
//            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        val surfaceView = findViewById<SurfaceView>(R.id.surface_view)
//        mPreviewHolder = surfaceView.holder
//        mPreviewHolder.addCallback(object : SurfaceHolder.Callback{
//            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
//                initCameraView()
//            }
//
//            override fun surfaceDestroyed(holder: SurfaceHolder) {
//                mCameraDevice.close()
//            }
//
//            override fun surfaceCreated(holder: SurfaceHolder) {
//                initCameraView()
//            }
//
//        })
//
//    }
//
//    private var mSessionCaptureSession: CameraCaptureSession.CaptureCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    object : CameraCaptureSession.CaptureCallback(){
//        override fun onCaptureFailed(session: CameraCaptureSession, request: CaptureRequest, failure: CaptureFailure) {
//            super.onCaptureFailed(session, request, failure)
//        }
//
//        override fun onCaptureCompleted(
//            session: CameraCaptureSession,
//            request: CaptureRequest,
//            result: TotalCaptureResult
//        ) {
//            mSession = session
//            unlockFocus()
//        }
//
//        override fun onCaptureProgressed(
//            session: CameraCaptureSession,
//            request: CaptureRequest,
//            partialResult: CaptureResult
//        ) {
//            mSession = session
//        }
//    }
//
//    private fun unlockFocus() {
//        mPreviewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL)
//        mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
//        mSession.capture(mPreviewBuilder.build(), mSessionCaptureSession, mHandler)
//        mSession.setRepeatingRequest(mPreviewBuilder.build(), mSessionCaptureSession, mHandler)
//    }
//
//
//    private fun takePicture() {
//        try {
//            mDeviceRotation = ORIENTATIONS.get(deviceOrientation.getOrientation())
//            var captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
//            captureRequestBuilder.addTarget(mImageReader.surface)
//            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
//            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
//            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION,mDeviceRotation)
//            var captureRequest = captureRequestBuilder.build()
//            mSession.capture(captureRequest, mSessionCaptureSession, mHandler)
//        }catch (e:Exception){
//            Log.d("takePicture",e.message.toString())
//        }
//    }
//
//    private fun getRotateBitmap(tempBitmap: Bitmap, degree: Int) : Bitmap? {
//        Log.d("getRotateBitmap", " true")
//        if(tempBitmap == null) return null
//        else if(degree == 0) return tempBitmap;
//        var matrix : Matrix = Matrix()
//        matrix.setRotate(degree.toFloat(),tempBitmap.width.toFloat()/2,tempBitmap.height.toFloat()/2);
//        return Bitmap.createBitmap(tempBitmap,0,0,tempBitmap.width,tempBitmap.height,matrix,true)
//    }
//
//    private var mOnImagageAvaliableListener : ImageReader.OnImageAvailableListener = @RequiresApi(Build.VERSION_CODES.KITKAT)
//    object : ImageReader.OnImageAvailableListener {
//        override fun onImageAvailable(reader: ImageReader?) {
//            var image = reader?.acquireLatestImage()
//            var buffer : ByteBuffer? = image?.planes?.get(0)?.buffer
//            var byteArray : ByteArray = ByteArray(buffer!!.remaining())
//            buffer.get(byteArray)
//            var bitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.size)
//            GlobalScope.launch {
//                initImage(bitmap)
//            }
//        }
//
//    }
//
//    private fun initImage(bitmap: Bitmap?) {
//        var rotateBitmap : Bitmap? = null;
//        try{
//            rotateBitmap = getRotateBitmap(bitmap!!,mDeviceRotation)
//        }catch (e : java.lang.Exception){
//            Log.d("saveFail","fail")
//        }
//        insertImage(contentResolver, rotateBitmap,"" + System.currentTimeMillis(),"")
//    }
//
//    private fun insertImage(contentResolver: ContentResolver?, rotateBitmap: Bitmap?, title: String, description: String) : String? {
//        var values : ContentValues = ContentValues()
//        values.put(MediaStore.Images.Media.TITLE, title)
//        values.put(MediaStore.Images.Media.DISPLAY_NAME, title);
//        values.put(MediaStore.Images.Media.DESCRIPTION, description);
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
//        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
//        if(contentResolver != null) {
//            var uri = contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//            var outputStream = uri?.let { contentResolver?.openOutputStream(it) }
//            rotateBitmap?.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
//            outputStream?.close()
//            return uri.toString()
//        }
//        return null
//    }
//
//
//    private fun openCamera() {
//        try{
//            val mCameraManager = this.getSystemService(Context.CAMERA_SERVICE) as CameraManager
//            val characteristics = mCameraManager.getCameraCharacteristics(this.mCameraId.toString())
//            val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
//            val largestPreviewSize = map!!.getOutputSizes(ImageFormat.JPEG)[0]
//            val mainHandler = Handler(mainLooper)
//            setAspectRatioTextureView(largestPreviewSize.height, largestPreviewSize.width)
//            mImageReader = ImageReader.newInstance(
//                largestPreviewSize.width,
//                largestPreviewSize.height,
//                ImageFormat.JPEG,
//                7
//            )
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//                return
//            }
//            mImageReader.setOnImageAvailableListener(mOnImagageAvaliableListener, mainHandler)
//            mCameraManager.openCamera(mCameraId.toString(), deviceStateCallback, mHandler)
//        }catch (e:Exception){
//            e.message?.let { Log.e("cameraError", it) }
//        }
//    }
//    private val deviceStateCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    object: CameraDevice.StateCallback() {
//        override fun onOpened(camera: CameraDevice) {
//            mCameraDevice = camera
//            try {
//                takePreview()
//            } catch (e: CameraAccessException) {
//                e.message?.let { Log.d("cameraError", it) }
//            }
//        }
//
//        override fun onDisconnected(camera: CameraDevice) {
//            mCameraDevice.close()
//        }
//
//        override fun onError(camera: CameraDevice, error: Int) {
//            Log.d("cameraOpenError",error.toString())
//        }
//    }
//
//    private fun takePreview() {
//        mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
//        mPreviewBuilder.addTarget(mPreviewHolder.surface)
//        mCameraDevice.createCaptureSession(
//            listOf(mPreviewHolder.surface, mImageReader.surface), mSessionPreviewStateCallback, mHandler
//        )
//    }
//    private val mSessionPreviewStateCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    object : CameraCaptureSession.StateCallback() {
//        override fun onConfigured(session: CameraCaptureSession) {
//            mSession = session
//            try {
//                // Key-Value 구조로 설정
//                // 오토포커싱이 계속 동작
//                mPreviewBuilder.set(
//                    CaptureRequest.CONTROL_AF_MODE,
//                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
//                )
//                //필요할 경우 플래시가 자동으로 켜짐
//                mPreviewBuilder.set(
//                    CaptureRequest.CONTROL_AE_MODE,
//                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
//                )
//                mSession.setRepeatingRequest(mPreviewBuilder.build(), null, mHandler)
//            } catch (e: CameraAccessException) {
//                Log.d("previewFail", e.printStackTrace().toString())
//            }
//
//        }
//
//        override fun onConfigureFailed(session: CameraCaptureSession) {
//
//        }
//    }
//
//    private fun setAspectRatioTextureView(height: Int, width: Int) {
//        if (width > height) {
//            val newWidth = mWidth
//            val newHeight = mWidth * width / height
//            updateTextureViewSize(newWidth, newHeight)
//
//        } else {
//            val newWidth = mWidth
//            val newHeight = mWidth * height / width
//            updateTextureViewSize(newWidth, newHeight)
//        }
//    }
//
//    private fun updateTextureViewSize(newWidth: Int, newHeight: Int) {
//        surface_view.layoutParams = FrameLayout.LayoutParams(newWidth, newHeight)
//    }
//
//    private fun initSize() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
//            mHeight= windowManager.currentWindowMetrics.bounds.height()
//            mWidth = windowManager.currentWindowMetrics.bounds.width()
//        }else{
//            mWidth = windowManager.defaultDisplay.width
//            mHeight = windowManager.defaultDisplay.height
//        }
//    }
//
//    private fun initCameraView() {
//        val handlerThread = HandlerThread("CAMERA2")
//        handlerThread.start()
//        mHandler = Handler(handlerThread.looper)
//        openCamera()
//    }

}