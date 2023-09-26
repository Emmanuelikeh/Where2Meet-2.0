package com.example.where2meet_20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.where2meet_20.databinding.ActivityLoginBinding
import com.example.where2meet_20.databinding.ActivitySignUpBinding
import com.parse.ParseUser
import com.parse.SignUpCallback

class SignUpActivity : AppCompatActivity() {
    private lateinit var activitySignUpActivity : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignUpActivity = ActivitySignUpBinding.inflate(layoutInflater)
        val view = activitySignUpActivity.root
        setContentView(view)


        activitySignUpActivity.tvLoginLink.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        activitySignUpActivity.btnSignUp.setOnClickListener{
            val signupUsername = activitySignUpActivity.etSignUpUserName.text.toString()
            val signupEmail = activitySignUpActivity.etSignUpEmail.text.toString()
            val signupPassword = activitySignUpActivity.etSignUpPassword.text.toString()
            val signUpRePassword = activitySignUpActivity.etSignUpRePassword.text.toString()
            signUpUser(signupEmail, signupUsername, signupPassword, signUpRePassword)
        }
    }

    private fun signUpUser(signupEmail: String, signupUsername: String, signupPassword: String, signUpRePassword: String) {
        if(!signupPassword.equals(signUpRePassword) && !signupPassword.equals("")){
            Toast.makeText(this,"Password mismatch", Toast.LENGTH_SHORT).show()
        }
        else{
            val parseUser = ParseUser()
            parseUser.email = signupEmail
            parseUser.setPassword(signupPassword)
            parseUser.username = signupUsername
            parseUser.signUpInBackground { SignUpCallback { e ->
                if (e == null){
                    Toast.makeText(this, "signup success", Toast.LENGTH_SHORT).show()
                    ParseUser.logOut();
                    goLoginActivity();
                }
            } }
        }
    }

    private fun goLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish();
    }
}