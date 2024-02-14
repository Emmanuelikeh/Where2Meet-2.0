package com.example.where2meet_20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.where2meet_20.databinding.FragmentPendingInviteBinding
import com.parse.ParseQuery
import com.parse.ParseUser


class PendingInviteFragment : Fragment() {
    private lateinit var fragmentPendingInviteBinding: FragmentPendingInviteBinding
    private lateinit var inviteList: ArrayList<Invite>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentPendingInviteBinding = FragmentPendingInviteBinding.inflate(inflater,container,false)
        val view = fragmentPendingInviteBinding.root
        return view
//        return inflater.inflate(R.layout.fragment_pending_invite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inviteList = ArrayList<Invite>()
        val adapter = InviteAdapter(inviteList,requireContext(), InviteAdapter.ScreenTypes.PENDING)
        fragmentPendingInviteBinding.rvPendingInvites.adapter = adapter
        fragmentPendingInviteBinding.rvPendingInvites.layoutManager = LinearLayoutManager(requireContext())
        queryInvite()
    }

        private fun queryInvite() {
        val query = ParseQuery.getQuery(Invite::class.java)
        query.whereEqualTo("Receiver", ParseUser.getCurrentUser())
        query.findInBackground { objects, e ->
            if (e == null) {
                inviteList.addAll(objects)
                fragmentPendingInviteBinding.rvPendingInvites.adapter?.notifyDataSetChanged()
            }
        }
    }


}