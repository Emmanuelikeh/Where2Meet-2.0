package com.example.where2meet_20

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.where2meet_20.databinding.FragmentPlaceSearchBinding
import okhttp3.Headers


class PlaceSearchFragment(searchFragment: SearchFragment) : Fragment() {
    private lateinit var fragmentPlaceSearchBinding: FragmentPlaceSearchBinding
    private val model: SharedViewModel by activityViewModels()
    lateinit var placeSearchList: ArrayList<PlaceSearch>
       lateinit var adapter: PlaceSearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentPlaceSearchBinding = FragmentPlaceSearchBinding.inflate(inflater,container,false)
        val view = fragmentPlaceSearchBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placeSearchList = ArrayList<PlaceSearch>()
        adapter = PlaceSearchResultAdapter(placeSearchList,requireContext())
        fragmentPlaceSearchBinding.rvPlaceList.adapter = adapter
        fragmentPlaceSearchBinding.rvPlaceList.layoutManager = LinearLayoutManager(requireContext())
        model.searchQuery.observe(viewLifecycleOwner) {
            Log.i("Hello", "searchQuery: $it")
            queryPlace(it)
        }
    }


    private fun queryPlace(s: String) {
        val API_KEY = getString(R.string.foursquare_api_key)
        // building the url
        var my_url = "https://api.foursquare.com/v3/places/search"

        // building the request
        val client = AsyncHttpClient()
        val headers = RequestHeaders()
        val params = RequestParams()
        params.put("query",s)
        headers.put("Authorization",API_KEY)
        client.get(my_url,headers,params, object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.d("PlaceSearchFragment", "onFailure: $response")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                val jsonObject = json?.jsonObject
                val results = jsonObject?.getJSONArray("results")
                placeSearchList.clear()
                placeSearchList.addAll(PlaceSearch.fromJsonArray(results))
                adapter.notifyDataSetChanged()
            }

        })



        }


    }


