package com.company.myapplication

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("password") val password: String?
)
