package com.example.mdaraplication.patientmain.fragment

import retrofit2.Call
import retrofit2.http.GET

interface Covid19Interface {
    @GET("patient/covid19")
    fun covid19(): Call<String>
}