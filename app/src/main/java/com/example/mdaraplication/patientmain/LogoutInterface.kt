package com.example.mdaraplication.patientmain

import retrofit2.Call
import retrofit2.http.GET

interface LogoutInterface {
    @GET("patient/logout")
    fun logout(): Call<String>
}