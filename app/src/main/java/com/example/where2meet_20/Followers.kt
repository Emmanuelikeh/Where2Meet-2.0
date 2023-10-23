package com.example.where2meet_20

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser


@ParseClassName("Followers")

class Followers: ParseObject() {
    val KEY_FOLLOWER = "Follower"
    val KEY_FOLLOWING = "Following"

    //set follower
    fun setFollower(follower: ParseUser){
        put(KEY_FOLLOWER, follower)
    }

    //get follower
    fun getFollower(): ParseUser?{
        return getParseUser(KEY_FOLLOWER)
    }

    //set following
    fun setFollowing(following: ParseUser){
        put(KEY_FOLLOWING, following)
    }




}