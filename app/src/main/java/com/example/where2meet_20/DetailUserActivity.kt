package com.example.where2meet_20

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.where2meet_20.databinding.ActivityDetailUserBinding
import com.parse.ParseQuery
import com.parse.ParseUser

class DetailUserActivity : AppCompatActivity() {
    private lateinit var activityDetailUserBinding: ActivityDetailUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parseUser = intent.extras!!["parseUser"] as ParseUser?
        val activityDetailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(activityDetailUserBinding.root)
        // update user information  on the screen
        val followObject = Followers()
        if (parseUser != null) {
            activityDetailUserBinding.tvUsername.text = parseUser.username
            activityDetailUserBinding.tvUserBio.text = parseUser.getString("userBiography")
            activityDetailUserBinding.ivUserProfileImage.load(R.drawable.image_app){
                placeholder(R.drawable.image_app)
            }
            followObject.getFollowerCount(parseUser, activityDetailUserBinding.tvFollowerCount)
            followObject.getFollowingCount(parseUser, activityDetailUserBinding.tvFollowingCount)
        }

        // check if the currentUser is following the user
        val currentUser = ParseUser.getCurrentUser()
        val query = ParseQuery.getQuery(Followers::class.java)
        query.whereEqualTo("Follower", currentUser)
        query.whereEqualTo("Following", parseUser)
        query.countInBackground { count, e ->
            if (e == null){
                if (count > 0){
                    activityDetailUserBinding.btnFollowUser.visibility = View.GONE
                    activityDetailUserBinding.btnUnFollowUser.visibility = View.VISIBLE
                }
                else{
                    activityDetailUserBinding.btnFollowUser.visibility = View.VISIBLE
                    activityDetailUserBinding.btnUnFollowUser.visibility = View.GONE
                }
            }
        }

        //follow the user
        activityDetailUserBinding.btnFollowUser.setOnClickListener{
            followObject.setFollower(currentUser)
            followObject.setFollowing(parseUser)
            followObject.saveInBackground()
            followObject.IncreaseLocalFollowerCount(activityDetailUserBinding.tvFollowerCount)
            activityDetailUserBinding.btnFollowUser.visibility = View.GONE
            activityDetailUserBinding.btnUnFollowUser.visibility = View.VISIBLE
            Toast.makeText(this, "You are now following ${parseUser?.username}", Toast.LENGTH_SHORT).show()
        }

        //unfollow the user
        activityDetailUserBinding.btnUnFollowUser.setOnClickListener{
            val query = ParseQuery.getQuery(Followers::class.java)
            query.whereEqualTo("Follower", currentUser)
            query.whereEqualTo("Following", parseUser)
            query.findInBackground{ followers, e ->
                if (e == null){
                    if (followers.size > 0){
                        followers[0].deleteInBackground()
                    }
                }
            }
            followObject.DecreaseLocalFollowerCount(activityDetailUserBinding.tvFollowerCount)
            activityDetailUserBinding.btnFollowUser.visibility = View.VISIBLE
            activityDetailUserBinding.btnUnFollowUser.visibility = View.GONE
            Toast.makeText(this, "You just unfollowed  ${parseUser?.username}", Toast.LENGTH_SHORT).show()
        }

    }
}