package com.company.myapplication

import com.google.gson.annotations.SerializedName

data class UserInfo (
    @SerializedName("username") val userName: String?,
    @SerializedName("email") val userEmail: String?
)