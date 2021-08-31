package com.example.mdaraplication.prescription

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface PrescriptionMakerInterface {
    @POST("/patient/insertPrescription")
    fun insertPrescription(@Query("prescription")prescription : String) : Call<String>
}