package com.example.where2meet_20

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.where2meet_20.databinding.FragmentSearchBinding
import com.parse.FindCallback
import com.parse.ParseUser
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONObject


class SearchFragment : Fragment() {

    lateinit var parseUserList: ArrayList<ParseUser>
    private lateinit var fragmentSearchBinding: FragmentSearchBinding
    lateinit var placeSearchList: ArrayList<PlaceSearch>

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
        queryPlace("Food")
        fragmentSearchBinding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                val currentUser = ParseUser.getCurrentUser().username
                val query = ParseUser.getQuery()
              query.whereNotEqualTo("username", currentUser)
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

    private fun queryPlace(s: String) {
        val API_KEY = getString(R.string.foursquare_api_key)
        val client = OkHttpClient()

        // building the url
        var my_url = "https://api.foursquare.com/v3/places/search?query=$s"

        val request = Request.Builder()
            .url(my_url)
            // add parameter
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization",API_KEY)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Toast.makeText(context, "Failed to get data", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val jsonData = response.body?.string()
                val jsonObject = JSONObject(jsonData)
                val results = jsonObject.getJSONArray("results")
                val place = results.getJSONObject(0)
                Log.i("SearchResult", "title: $place")

            }
        })


    }


}