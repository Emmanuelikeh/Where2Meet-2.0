package com.example.where2meet_20

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.where2meet_20.databinding.FragmentHomeBinding
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONObject


class HomeFragment : Fragment() {
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    val NOW_PLAYING =
        "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater);
        val view = fragmentHomeBinding.root

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(NOW_PLAYING)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Toast.makeText(context, "Failed to get data", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val jsonData = response.body?.string()
                val jsonObject = JSONObject(jsonData)
                val results = jsonObject.getJSONArray("results")
                val movie = results.getJSONObject(0)
                val title = movie.getString("title")
                val overview = movie.getString("overview")
                Log.i("HomeFragment", "title: $title")
                Log.i("HomeFragment", "overview: $overview")
            }
        })

        return view
        }

    }
