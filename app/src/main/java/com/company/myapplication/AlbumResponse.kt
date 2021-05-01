package com.company.myapplication

import com.google.gson.annotations.SerializedName

data class AlbumResponse (
    @SerializedName("albumId") val id: Int?,
    @SerializedName("albumDescription") val description: String?,
    @SerializedName("albumName") val name: String?,
    @SerializedName("albumCover") val cover: String?,
    @SerializedName("user") val user: String?,
    @SerializedName("private") val private: Boolean?
)