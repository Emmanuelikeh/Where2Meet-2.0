package com.example.where2meet_20

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser


@ParseClassName("Messages")
class Messages : ParseObject() {
    fun setGroupId(invite: Invite?) {
        put(KEY_GROUP_ID, invite!!)
    }

    var messageSender: ParseUser?
        get() = getParseUser(KEY_MESSAGE_SENDER)
        set(sender) {
            put(KEY_MESSAGE_SENDER, sender!!)
        }
    var messageBody: String?
        get() = getString(KEY_BODY)
        set(body) {
            put(KEY_BODY, body!!)
        }

    companion object {
        const val KEY_GROUP_ID = "groupId"
        const val KEY_MESSAGE_SENDER = "Sender"
        const val KEY_BODY = "Body"
    }
}