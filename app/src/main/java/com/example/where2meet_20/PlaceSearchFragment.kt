package com.example.where2meet_20

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        var location = getUserLocation()

        // building the request
        val client = AsyncHttpClient()
        val headers = RequestHeaders()
        val params = RequestParams()
        params["query"] = s
//        if (location != null) {
//            Log.i(tag, "queryPlace: ${getLatitudeAndLongitude(location)}")
//            params["ll"] = getLatitudeAndLongitude(location)
//        }
        headers["Authorization"] = API_KEY
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

    private fun getLatitudeAndLongitude(location: Location): String? {
        val longitude = location.longitude.toString()
        val latitude = location.latitude.toString()
        return "$latitude,$longitude"
    }

    fun getUserLocation(): Location? {
        val activity = activity as MainActivity?
        return activity!!.usersLocation
    }



}




