package com.example.mdaraplication.findPW

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FindPWInterface {
    @FormUrlEncoded
    @POST("patient/pwCheck")
    fun findPW(@Field("id")email:String): Call<String>
}