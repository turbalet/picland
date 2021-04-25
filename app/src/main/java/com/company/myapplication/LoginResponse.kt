package com.company.myapplication

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("token")
    var token: String,

    @SerializedName("username")
    var username: String,

    @SerializedName("refreshToken")
    var refreshToken: String
)