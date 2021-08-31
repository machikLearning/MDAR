package com.example.mdaraplication.signup

import android.provider.ContactsContract
import com.example.mdaraplication.signup.data.SignUpData
import com.example.mdaraplication.signup.data.SignupRequestData
import retrofit2.Call
import retrofit2.http.*

interface SignupInterface {
    @FormUrlEncoded
    @POST("patient/joinCheck")
    fun signup(@Field("id") userId : String, @Field("pw")pw:String, @Field("email")email: String,@Field("code")cbnuCode:String,@Field("role2") doctor:Boolean,
               @Field("role1")patient:Boolean, @Field("hospital_id") hospital : Int,@Field("name")userName:String) : Call<String>
}