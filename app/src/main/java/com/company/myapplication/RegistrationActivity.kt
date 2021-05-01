package com.company.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegistrationActivity : AppCompatActivity() {

    lateinit var username1: EditText
    lateinit var email1:EditText
    lateinit var password1: EditText

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val intent = Intent(this, AuthorizationActivity::class.java)

        val button = findViewById<Button>(R.id.signup)
        button.setOnClickListener{
            username1 = findViewById(R.id.reg_username)
            val username = username1.text.toString()
            email1 = findViewById(R.id.reg_email)
            val email = email1.text.toString()
            password1 = findViewById(R.id.reg_password)
            val password = password1.text.toString()

            apiClient = ApiClient()
            sessionManager = SessionManager(this)

            apiClient.getApiService().signUp(
                SignUpRequest(
                    username = username,
                    email = email,
                    password = password
                )
            )
                .enqueue(object : Callback<SignUpResponse> {
                    override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                        println(t)
                    }

                    override fun onResponse(
                        call: Call<SignUpResponse>,
                        response: Response<SignUpResponse>
                    ) {
                        val loginResponse = response.body()
                        val response = "success"
                                //loginResponse?.response.toString()

                        println(response)

                        if (response != null) {
                            val toast = Toast.makeText(
                                applicationContext,
                                response,
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                            startActivity(intent)
                        }
                    }

                })
        }
    }

}