package com.example.where2meet_20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import coil.load
import com.example.where2meet_20.databinding.ActivityEditProfileBinding
import com.example.where2meet_20.databinding.ActivityMainBinding
import com.parse.ParseUser
import com.parse.SaveCallback
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

        activityEditProfileBinding.btnCancel.setOnClickListener{
            finish()
        }
        activityEditProfileBinding.btnDone.setOnClickListener{
            currentUser.username = activityEditProfileBinding.etUserNamechange.text.toString()
            currentUser.put("userBiography",activityEditProfileBinding.etCurrentUserBio.text.toString())
            currentUser.saveInBackground { SaveCallback { e ->
                if (e != null){
                    Toast.makeText(this,e.message.toString(),Toast.LENGTH_SHORT).show()

                }
            } }
            finish()

        }
    }

}