package com.example.where2meet_20

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.where2meet_20.databinding.ActivityMessageActivtyBinding
import com.parse.FindCallback
import com.parse.ParseQuery
import com.parse.ParseUser


class MessageActivity : AppCompatActivity() {
    private lateinit var messageBinding : ActivityMessageActivtyBinding
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Messages>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messageBinding = ActivityMessageActivtyBinding.inflate(layoutInflater)
        setContentView(messageBinding.root)
        
        messageBinding.sendButton.setOnClickListener {
            processMessage()
        }

        messageList = ArrayList()
        messageAdapter = MessageAdapter(messageList, this)
        messageBinding.rvMessages.adapter = messageAdapter
        messageBinding.rvMessages.layoutManager = LinearLayoutManager(this)
        queryChats();


    }

    private fun queryChats() {
        val query = ParseQuery.getQuery(
            Messages::class.java
        )
        val currentGroupId = getGroup()
        query.whereEqualTo(Messages.KEY_GROUP_ID, currentGroupId)
        query.include(Messages.KEY_MESSAGE_SENDER)
        query.findInBackground { messages, e ->
            if (e == null) {
                messageList.addAll(messages)
                messageBinding.rvMessages.scrollToPosition(messageList.size - 1)
                messageAdapter.notifyDataSetChanged()
            }
        }

    }


    private fun processMessage() {
        val message = messageBinding.messageInput.text.toString()
        if(message.isEmpty()){
            return
        }

        val specificGroup = getGroup()
        val sendMessage = Messages()
        sendMessage.messageSender = ParseUser.getCurrentUser()
        sendMessage.messageBody = message
        sendMessage.setGroupId(specificGroup)
        sendMessage.saveInBackground{
            if(it == null){
                //success
                messageBinding.messageInput.setText("")
            }
        }

        // add message to the recycler view
        messageList.add(sendMessage)
        messageAdapter.notifyDataSetChanged()



    }

    fun getGroup(): Invite? {
        return intent.extras!!["inviteInfo"] as Invite?
    }



}