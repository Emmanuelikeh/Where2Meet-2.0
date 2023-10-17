package com.example.where2meet_20

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.where2meet_20.databinding.FragmentSearchBinding
import com.parse.FindCallback
import com.parse.ParseUser


class SearchFragment : Fragment() {
    lateinit var parseUserList: ArrayList<ParseUser>
    private lateinit var fragmentSearchBinding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater,container,false)
        val view = fragmentSearchBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseUserList = ArrayList<ParseUser>()
        val adapter = searchResultAdapter(parseUserList,requireContext())
        fragmentSearchBinding.rvItemsList.adapter = adapter
        fragmentSearchBinding.rvItemsList.layoutManager = LinearLayoutManager(context)
        fragmentSearchBinding.searchView.clearFocus()
        fragmentSearchBinding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                val currentUser = ParseUser.getCurrentUser().username
                val query = ParseUser.getQuery()
//               query.whereNotEqualTo("username", currentUser)
                query.whereStartsWith("username",s)
                query.findInBackground(FindCallback { objects, e ->
                    adapter.clear()
                    if (e == null){
                        parseUserList.addAll(objects)
                        adapter.notifyDataSetChanged()
                    }
                })
                return true
            }
        })
    }


}