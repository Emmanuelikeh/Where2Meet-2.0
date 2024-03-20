package com.example.where2meet_20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.where2meet_20.databinding.ActivityChatBinding
import com.parse.ParseQuery
import com.parse.ParseUser

class ChatActivity : AppCompatActivity() {
    private lateinit var chatBinding: ActivityChatBinding
    private lateinit var chatList: ArrayList<Invite>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(chatBinding.root)

        chatList = ArrayList<Invite>()
        val adapter = InviteAdapter(chatList, this, InviteAdapter.ScreenTypes.ACCEPTED)
        chatBinding.rvChats.adapter = adapter
        chatBinding.rvChats.layoutManager = LinearLayoutManager(this)
        queryChats()
    }

    private fun queryChats(){
        val query = ParseQuery.getQuery(Invite::class.java)
        query.whereEqualTo("Sender", ParseUser.getCurrentUser())
        query.whereEqualTo(Invite.KEY_FLAG,2)
        query.findInBackground { objects, e ->
            if (e == null) {
                chatList.addAll(objects)
                chatBinding.rvChats.adapter?.notifyDataSetChanged()
            }
        }
    }
}