package com.example.where2meet_20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val signUpLink : View = findViewById(R.id.tvSignupLink)
        signUpLink.setOnClickListener {
           val intent =  Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}