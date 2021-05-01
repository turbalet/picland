package com.company.myapplication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST(Constants.LOGIN_URL)
    fun login(@Body request: UserLogin): Call<LoginResponse>

    @POST(Constants.SIGNUP_URL)
    fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>

    @GET(Constants.ALBUM_URL)
    fun getAlbums(
        @Header("Authorization") authHeader: String?): Call<AlbumResponse>

    @GET(Constants.USER_URL)
    fun getUserInfo(@Body request: LoginResponse): Call<UserInfo>


}