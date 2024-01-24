package com.example.where2meet_20

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.where2meet_20.databinding.FragmentAccountSearchBinding
import com.example.where2meet_20.databinding.FragmentPlaceSearchBinding
import com.parse.ParseUser


class AccountSearchFragment : Fragment() {
    private lateinit var fragmentAccountSearchBinding: FragmentAccountSearchBinding
    private val model: SharedViewModel by activityViewModels()
    lateinit var parseUserList: ArrayList<ParseUser>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentAccountSearchBinding = FragmentAccountSearchBinding.inflate(inflater,container,false)
        val view = fragmentAccountSearchBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseUserList = ArrayList<ParseUser>()
        val adapter = searchResultAdapter(parseUserList,requireContext())
        fragmentAccountSearchBinding.rvAccountList.adapter = adapter
        fragmentAccountSearchBinding.rvAccountList.layoutManager = LinearLayoutManager(requireContext())

        model.searchQuery.observe(viewLifecycleOwner) {
            val currentUser = ParseUser.getCurrentUser().username
            val query = ParseUser.getQuery()
            query.whereNotEqualTo("username", currentUser)
            query.whereContains("username", it)
            query.findInBackground { objects, e ->
                adapter.clear()
                if (e == null) {
                    parseUserList.addAll(objects)
                    adapter.notifyDataSetChanged()
                }
            }

        }
    }


}