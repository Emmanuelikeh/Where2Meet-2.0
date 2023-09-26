package com.example.where2meet_20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.where2meet_20.databinding.ActivityLoginBinding
import com.parse.LogInCallback
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var activityLoginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = activityLoginBinding.root
        setContentView(view)

        activityLoginBinding.btnLogin.setOnClickListener{
            val username = activityLoginBinding.etUserName.text.toString()
            val password = activityLoginBinding.etUserPassword.text.toString()
            logInUser(username, password)
        }

        activityLoginBinding.tvSignupLink.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

        }

    }

    private fun logInUser(username: String, password: String) {
        ParseUser.logInInBackground(username, password, LogInCallback { user, e ->
            if (e != null){
                Toast.makeText(this,e.message, Toast.LENGTH_SHORT).show()

            }
            else{
                goMainActivity();
            }
        })

    }

    private fun goMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}