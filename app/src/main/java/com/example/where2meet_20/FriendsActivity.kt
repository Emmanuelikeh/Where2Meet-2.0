package com.example.where2meet_20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.where2meet_20.databinding.ActivityFriendsBinding
import com.parse.ParseQuery
import com.parse.ParseUser

class FriendsActivity : AppCompatActivity() {
    private lateinit var activityFriendsBinding: ActivityFriendsBinding
    lateinit var parseUserList: ArrayList<Followers>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFriendsBinding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(activityFriendsBinding.root)
        parseUserList = ArrayList<Followers>()
        val adapter = FriendAdapter(parseUserList,this)
        activityFriendsBinding.rvPerspectiveFriendsList.adapter = adapter
        activityFriendsBinding.rvPerspectiveFriendsList.layoutManager = LinearLayoutManager(this)
        queryFriend("")
    }

    private fun queryFriend(s: String) {
        val query = ParseQuery.getQuery(Followers::class.java)
        query.whereEqualTo("Follower", ParseUser.getCurrentUser())
        query.findInBackground { objects, e ->
            if (e == null) {
                parseUserList.addAll(objects)
                activityFriendsBinding.rvPerspectiveFriendsList.adapter?.notifyDataSetChanged()
            }
        }


    }
}