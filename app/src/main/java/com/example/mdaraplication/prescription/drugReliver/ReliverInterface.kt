package com.example.mdaraplication.prescription.drugReliver

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ReliverInterface {
    @GET("/patient/relivedDrug")
    fun relivingDrug(@Query("searchParam")searchParam:String) : Call<String>
}