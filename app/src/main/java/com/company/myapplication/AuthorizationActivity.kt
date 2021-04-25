package com.company.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorizationActivity : AppCompatActivity() {



    lateinit var etEmail: EditText
    lateinit var  etPassword: EditText

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        val button = findViewById<Button>(R.id.login)
        button.setOnClickListener{
            etEmail = findViewById(R.id.email_input)
            val email = etEmail.text.toString()
            etPassword = findViewById(R.id.password_input)
            val password = etPassword.text.toString()

            apiClient = ApiClient()
            sessionManager = SessionManager(this)

            val intent = Intent(this, MainActivity::class.java)

            apiClient.getApiService().login(UserLogin(username = email, password = password))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        println(t)
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        val loginResponse = response.body()

                        if (loginResponse != null) {
                            println("success!!!")
                            sessionManager.saveAuthToken(loginResponse.token)
                            startActivity(intent)
                        }
                    }

                })

        }

        val signuptextview = findViewById<TextView>(R.id.auth_signup)
        signuptextview.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

    }

}