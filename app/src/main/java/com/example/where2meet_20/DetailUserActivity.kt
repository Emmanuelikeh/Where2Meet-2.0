package com.example.where2meet_20

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.where2meet_20.databinding.ActivityDetailUserBinding
import com.parse.ParseUser

class DetailUserActivity : AppCompatActivity() {
    private lateinit var activityDetailUserBinding: ActivityDetailUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityDetailUserBinding  = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(activityDetailUserBinding.root)
        val parseUser = intent.extras!!["parseUser"] as ParseUser?
        if (parseUser != null) {
            activityDetailUserBinding.tvUsername.text = parseUser.username
            activityDetailUserBinding.tvUserBio.text = parseUser.getString("userBiography")
        }
       activityDetailUserBinding.ivUserProfileImage.load(R.drawable.image_app){
            placeholder(R.drawable.image_app)
        }


    }
}