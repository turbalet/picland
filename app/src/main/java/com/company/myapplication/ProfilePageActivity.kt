package com.company.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilePageActivity : AppCompatActivity() {

    lateinit var usernameTV: TextView
    lateinit var  emailTV: TextView
    lateinit var profileIV: ImageView
    lateinit var albumName: TextView
    lateinit var recyclerView: RecyclerView

    private lateinit var linearLayoutManager: LinearLayoutManager


    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        recyclerView = findViewById(R.id.activity_profile_page_recycler_view)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        usernameTV = findViewById(R.id.profile_page_username_tv)
        emailTV = findViewById(R.id.profile_page_email_tv)

        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        var username = sharedPreference.getString("username","defaultName")
        var email = sharedPreference.getString("email","defaultName")
        usernameTV.setText(username)
        emailTV.setText(email)


        apiClient = ApiClient()
        sessionManager = SessionManager(this)


        /*apiClient.getApiService().getAlbums(sessionManager.fetchAuthToken())
            .enqueue(object : Callback<AlbumResponse> {
                override fun onFailure(call: Call<AlbumResponse>, t: Throwable) {
                    println(t)
                }

                override fun onResponse(
                    call: Call<AlbumResponse>,
                    response: Response<AlbumResponse>
                ) {
                    val albumResponse = response.body()
                    var nameAlbum = albumResponse!!.name
                    albumName.setText(nameAlbum)
                }

            })*/

    }
}