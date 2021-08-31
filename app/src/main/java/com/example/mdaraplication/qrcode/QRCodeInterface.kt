package com.example.mdaraplication.qrcode

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QRCodeInterface {
    @GET("patient/checkQRcode")
    fun checkQRCode(@Query("codes") codeList : List<String>) : Call<String>
}