package com.example.where2meet_20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.where2meet_20.databinding.ActivityInvitationsBinding
import com.parse.ParseQuery
import com.parse.ParseUser

class InvitationsActivity : AppCompatActivity() {
    private lateinit var activityInvitationsBinding: ActivityInvitationsBinding
//    private lateinit var inviteList: ArrayList<Invite>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_invitations)
        activityInvitationsBinding = ActivityInvitationsBinding.inflate(layoutInflater)
        setContentView(activityInvitationsBinding.root)
         addFragment()
//        inviteList = ArrayList<Invite>()
//        val adapter = InviteAdapter(inviteList,this)
//        activityInvitationsBinding.rvInvitations.adapter = adapter
//        activityInvitationsBinding.rvInvitations.layoutManager = LinearLayoutManager(this)
//        queryInvite()

    }

    private fun addFragment() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(PendingInviteFragment(),"Pending")
        adapter.addFragment(SentInviteFragment(),"Sent")
        activityInvitationsBinding.vpInvitations.adapter = adapter;
        activityInvitationsBinding.tbLayout.setupWithViewPager(activityInvitationsBinding.vpInvitations);
    }

//    private fun queryInvite() {
//        val query = ParseQuery.getQuery(Invite::class.java)
//        query.whereEqualTo("Receiver", ParseUser.getCurrentUser())
//        query.findInBackground { objects, e ->
//            if (e == null) {
//                inviteList.addAll(objects)
//                activityInvitationsBinding.rvInvitations.adapter?.notifyDataSetChanged()
//            }
//        }
//    }
}