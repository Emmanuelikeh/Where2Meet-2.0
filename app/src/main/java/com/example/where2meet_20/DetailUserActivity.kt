package com.example.where2meet_20

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.where2meet_20.databinding.ActivityDetailUserBinding
import com.parse.ParseQuery
import com.parse.ParseUser

class DetailUserActivity : AppCompatActivity() {
    private lateinit var activityDetailUserBinding: ActivityDetailUserBinding

//    private fun getFollowingCount(parseUser: ParseUser){
//        val query = ParseQuery.getQuery(Followers::class.java)
//        query.whereEqualTo("Follower", parseUser)
//        query.countInBackground { count, e ->
//            if (e == null){
//               activityDetailUserBinding.tvFollowingCount.text = count.toString()
//            }
//        }
//    }
//
//    // get the follower count
//    private fun getFollowersCount(parseUser: ParseUser){
//        val query = ParseQuery.getQuery(Followers::class.java)
//        query.whereEqualTo("Following", parseUser)
//        query.findInBackground() { count, e ->
//            if (e == null){
//                activityDetailUserBinding.tvFollowerCount.text = count.size.toString()
//            }
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityDetailUserBinding  = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(activityDetailUserBinding.root)
        val parseUser = intent.extras!!["parseUser"] as ParseUser?
        if (parseUser != null) {
            activityDetailUserBinding.tvUsername.text = parseUser.username
            activityDetailUserBinding.tvUserBio.text = parseUser.getString("userBiography")

            var query = ParseQuery.getQuery(Followers::class.java)
            query.whereEqualTo("Follower", parseUser)
            query.countInBackground { count, e ->
            if (e == null){
               activityDetailUserBinding.tvFollowingCount.text = count.toString()
            }
                query = ParseQuery.getQuery(Followers::class.java)
                query.whereEqualTo("Following", parseUser)
                query.findInBackground() { count, e ->
            if (e == null){
                activityDetailUserBinding.tvFollowerCount.text = count.size.toString()
            }
        }

        }

        }


       activityDetailUserBinding.ivUserProfileImage.load(R.drawable.image_app){
            placeholder(R.drawable.image_app)
        }

        // check if the current user is following the other user
        var query = ParseQuery.getQuery(Followers::class.java)
        query.whereEqualTo("Follower", ParseUser.getCurrentUser())
        query.whereEqualTo("Following", parseUser)
        query.findInBackground { followers, e ->
            if (e == null){
                if (followers.size > 0){
                    activityDetailUserBinding.btnFollowUser.visibility = View.GONE
                    activityDetailUserBinding.btnUnfollowUser.visibility = View.VISIBLE
                }
            }
        }


        activityDetailUserBinding.btnFollowUser.setOnClickListener(){
            val followers = Followers()
            followers.setFollower(ParseUser.getCurrentUser())
            if (parseUser != null) {
                followers.setFollowing(parseUser)
            }
            followers.saveInBackground{
                if (it == null){
                    query = ParseQuery.getQuery(Followers::class.java)
                    query.whereEqualTo("Follower", parseUser)
                    query.countInBackground { count, e ->
                        if (e == null){
                            activityDetailUserBinding.tvFollowingCount.text = count.toString()
                        }
                        query = ParseQuery.getQuery(Followers::class.java)
                        query.whereEqualTo("Following", parseUser)
                        query.findInBackground() { count, e ->
                            if (e == null){
                                activityDetailUserBinding.tvFollowerCount.text = count.size.toString()
                            }


                }
            }
            activityDetailUserBinding.btnFollowUser.visibility = View.GONE
            activityDetailUserBinding.btnUnfollowUser.visibility = View.VISIBLE
        }

        activityDetailUserBinding.btnUnfollowUser.setOnClickListener(){
            var query = ParseQuery.getQuery(Followers::class.java)
            query.whereEqualTo("Follower", ParseUser.getCurrentUser())
            query.whereEqualTo("Following", parseUser)
            query.findInBackground { followers, e ->
                if (e == null){
                    if (followers.size > 0){
                        followers[0].deleteInBackground{
                            if (it == null){
                                query = ParseQuery.getQuery(Followers::class.java)
                                query.whereEqualTo("Follower", parseUser)
                                query.countInBackground { count, e ->
                                    if (e == null){
                                        activityDetailUserBinding.tvFollowingCount.text = count.toString()
                                    }
                                    query = ParseQuery.getQuery(Followers::class.java)
                                    query.whereEqualTo("Following", parseUser)
                                    query.findInBackground() { count, e ->
                                        if (e == null){
                                            activityDetailUserBinding.tvFollowerCount.text = count.size.toString()
                                        }
                            }
                    }
                }
            }
//            getFollowersCount(parseUser!!)
//            getFollowingCount(parseUser)
            activityDetailUserBinding.btnFollowUser.visibility = View.VISIBLE
            activityDetailUserBinding.btnUnfollowUser.visibility = View.GONE
        }

    }

}
}}}}}