package com.example.mdaraplication.findID

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FindIDInteface {
    @FormUrlEncoded
    @POST("patient/idCheck")
    fun findID(@Field("mail")email:String): Call<String>
}