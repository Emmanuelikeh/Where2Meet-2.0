package com.example.where2meet_20

import android.widget.TextView
import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser


@ParseClassName("Followers")

class Followers: ParseObject() {
    val KEY_FOLLOWER = "Follower"
    val KEY_FOLLOWING = "Following"

    //set follower
    fun setFollower(follower: ParseUser){
        put(KEY_FOLLOWER, follower)
    }


    //set following
    fun setFollowing(following: ParseUser?){
        if (following != null) {
            put(KEY_FOLLOWING, following)
        }
    }

    // get follower count for a user
    fun getFollowerCount(user: ParseUser?, tvFollowerCount: TextView) {
        var query = ParseQuery.getQuery(Followers::class.java)
        query.whereEqualTo(KEY_FOLLOWING, user)
        query.countInBackground(){ count, e ->
            if (e == null){
                tvFollowerCount.text = count.toString()
            }
        }
    }

    // get following count for a user
    fun getFollowingCount(user: ParseUser?, tvFollowingCount: TextView) {
        var query = ParseQuery.getQuery(Followers::class.java)
        query.whereEqualTo(KEY_FOLLOWER, user)
        query.countInBackground(){ count, e ->
            if (e == null){
                tvFollowingCount.text = count.toString()
            }
        }
    }

    // increase client rendering of follower count
    fun IncreaseLocalFollowerCount(tvFollowerCount: TextView){
        var count = tvFollowerCount.text.toString().toInt()
        count++
        tvFollowerCount.text = count.toString()
    }
    //decrease client rendering of follower count
    fun DecreaseLocalFollowerCount(tvFollowerCount: TextView){
        var count = tvFollowerCount.text.toString().toInt()
        count--
        tvFollowerCount.text = count.toString()
    }


}