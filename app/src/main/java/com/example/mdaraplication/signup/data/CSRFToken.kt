package com.example.mdaraplication.signup.data

import com.google.gson.annotations.SerializedName

data class CSRFToken (
    @SerializedName("_csrf")
    var token : String
)