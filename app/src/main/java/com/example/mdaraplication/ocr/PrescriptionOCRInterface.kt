package com.example.mdaraplication.ocr

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface PrescriptionOCRInterface {

    @POST("patient/uploadFile")
    @Multipart
    fun uploadPicture(@Part picture : MultipartBody.Part) : Call<String>
}