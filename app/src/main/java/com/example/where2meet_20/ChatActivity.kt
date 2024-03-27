package com.example.where2meet_20

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        val sender = ParseQuery.getQuery(Invite::class.java)
        sender.whereEqualTo(Invite.KEY_RECEIVER, ParseUser.getCurrentUser())

        val receiver = ParseQuery.getQuery(Invite::class.java)
        receiver.whereEqualTo(Invite.KEY_SENDER, ParseUser.getCurrentUser())

        val queries: MutableList<ParseQuery<Invite>> = ArrayList()
        queries.add(sender)
        queries.add(receiver)
        val query = ParseQuery.getQuery(Invite::class.java)
        query.include(Invite.KEY_SENDER)
        query.include(Invite.KEY_RECEIVER)
        ParseQuery.or(queries)
        query.whereEqualTo(Invite.KEY_FLAG, 2)
        query.addAscendingOrder(Invite.KEY_INVITATION_DATE)
        query.findInBackground { objects, e ->
            if (e == null) {
                chatList.addAll(objects)
                chatBinding.rvChats.adapter?.notifyDataSetChanged()
            }
        }
    }
}