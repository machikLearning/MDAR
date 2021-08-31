package com.example.mdaraplication.doctorReliever

import retrofit2.Call
import retrofit2.http.GET

interface DoctorRelieverInterface {
    @GET("/patient/relivedClinic")
    fun relivingDrug() : Call<String>
}