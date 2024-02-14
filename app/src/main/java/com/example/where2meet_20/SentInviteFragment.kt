package com.example.where2meet_20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.where2meet_20.databinding.FragmentSentInviteBinding
import com.parse.ParseQuery
import com.parse.ParseUser


class SentInviteFragment : Fragment() {
    private lateinit var fragmentSentInviteBinding: FragmentSentInviteBinding
    private lateinit var inviteList: ArrayList<Invite>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSentInviteBinding = FragmentSentInviteBinding.inflate(inflater,container,false)
        val view = fragmentSentInviteBinding.root
        return view
//        return inflater.inflate(R.layout.fragment_sent_invite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inviteList = ArrayList<Invite>()
        val adapter = InviteAdapter(inviteList,requireContext(), InviteAdapter.ScreenTypes.SENT)
        fragmentSentInviteBinding.sentInviteRecyclerView.adapter = adapter
        fragmentSentInviteBinding.sentInviteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        querySentInvite()
    }

    private fun querySentInvite() {
        val query = ParseQuery.getQuery(Invite::class.java)
        query.include(Invite.KEY_RECEIVER)
        query.addDescendingOrder(Invite.KEY_CREATED_AT)
        query.whereEqualTo(Invite.KEY_SENDER, ParseUser.getCurrentUser())
        query.findInBackground { objects, e ->
            if (e == null) {
                inviteList.addAll(objects)
                fragmentSentInviteBinding.sentInviteRecyclerView.adapter?.notifyDataSetChanged()
            }
        }

    }

}