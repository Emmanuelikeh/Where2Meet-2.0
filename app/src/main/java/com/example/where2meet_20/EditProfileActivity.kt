package com.example.where2meet_20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.example.where2meet_20.databinding.ActivityEditProfileBinding
import com.example.where2meet_20.databinding.ActivityMainBinding
import com.parse.ParseUser
import java.util.zip.Inflater

class EditProfileActivity : AppCompatActivity() {
    private lateinit var activityEditProfileBinding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEditProfileBinding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(activityEditProfileBinding.root)

        activityEditProfileBinding.ivUserProfileImg.load(R.drawable.image_app)
        var currentUser = ParseUser.getCurrentUser()
        activityEditProfileBinding.etUserNamechange.setText(currentUser.username)
        activityEditProfileBinding.etCurrentUserBio.setText(currentUser.getString("userBiography"))
    }
}