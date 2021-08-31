package com.example.mdaraplication.ocr

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mdaraplication.R
import com.example.mdaraplication.util.Util
import kotlinx.android.synthetic.main.ocr_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class OCRMain : AppCompatActivity() {
    private val OPEN_GALLERY = 2;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ocr_main)
        select_picture.setOnClickListener { openGallery() }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == OPEN_GALLERY){
                var selectedPictureURI : Uri? = data?.data
                if(selectedPictureURI != null){
                    operationUpload(selectedPictureURI)
                }
            }
        }
    }

    private fun operationUpload(selectedPictureURI: Uri) {
        val bitmap = buildBitmap(selectedPictureURI)
        val fileImage : File = convertBitampToFile(bitmap)
        var requestBody = RequestBody.create(MediaType.parse("text/plain"),fileImage)
        var body:MultipartBody.Part = MultipartBody.Part.createFormData("upload_file",fileImage.name,requestBody)
        try{
            connectServer(body)
        }catch (error : Exception){

        }finally {
            fileImage.deleteOnExit()
        }
    }

    private fun connectServer(body: MultipartBody.Part) {
        var retrofit = Util.getRetrofit()
        var prescriptionOCRService = retrofit.create(PrescriptionOCRInterface::class.java)
        GlobalScope.launch {
            val message = async {prescriptionOCRService.uploadPicture(body).execute().body()}
        }

    }


    private fun convertBitampToFile(bitmap: Bitmap): File {
        val fileName : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storage : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image : File = File.createTempFile(fileName, ".jpg",storage);
        return image
    }


    private fun buildBitmap(selectedPictureURI: Uri): Bitmap {
        val bitmap : Bitmap
        if(Build.VERSION.SDK_INT < 28) {
            bitmap = MediaStore.Images.Media.getBitmap(
                this.contentResolver,
                selectedPictureURI
            )
        } else {
            val source = ImageDecoder.createSource(this.contentResolver, selectedPictureURI)
            bitmap = ImageDecoder.decodeBitmap(source)
        }
        return bitmap
    }
}
