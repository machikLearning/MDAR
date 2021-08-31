package com.example.mdaraplication.login.data

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("userID")
    var userID : String,

    @SerializedName("password")
    var password:String
)