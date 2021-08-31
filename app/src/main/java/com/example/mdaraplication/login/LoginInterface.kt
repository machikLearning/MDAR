package com.example.mdaraplication.login

import retrofit2.Call
import retrofit2.http.*

interface LoginInterface {
    @FormUrlEncoded
    @POST("patient/loginCheck")
    fun requestLogin(@Field("id")userID:String, @Field("pw")password:String) : Call<String>

    @GET("patient/main")
    fun requestDoctorMain(@Header("Cookie") sessionID : String) :Call<String>

}