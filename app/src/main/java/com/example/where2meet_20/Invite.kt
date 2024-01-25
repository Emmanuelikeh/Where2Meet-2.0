package com.example.where2meet_20

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.Date


@ParseClassName("Invite")
class Invite : ParseObject() {

    var address: String?
        get() = getString(KEY_ADDRESS)
        set(address) {
            put(KEY_ADDRESS, address!!)
        }
    var title: String?
        get() = getString(KEY_TITLE)
        set(title) {
            put(KEY_TITLE, title!!)
        }
    var invitationDate: Date?
        get() = getDate(KEY_INVITATION_DATE)
        set(invitationDate) {
            if (invitationDate != null) {
                put(KEY_INVITATION_DATE, invitationDate)
            }
        }
    var sender: ParseUser?
        get() = getParseUser(KEY_SENDER)
        set(sender) {
            put(KEY_SENDER, sender!!)
        }
    var flag: Int
        get() = getInt(KEY_FLAG)
        set(flag) {
            put(KEY_FLAG, flag)
        }
    var receiver: ParseUser?
        get() = getParseUser(KEY_RECEIVER)
        set(receiver) {
            put(KEY_RECEIVER, receiver!!)
        }

    companion object {
        const val KEY_ADDRESS = "Address"
        const val KEY_SENDER = "Sender"
        const val KEY_RECEIVER = "Receiver"
        const val KEY_INVITATION_DATE = "invitationDate"
        const val KEY_TITLE = "Title"
        const val KEY_FLAG = "Flag"
    }
}