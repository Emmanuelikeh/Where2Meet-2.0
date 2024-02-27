package com.example.where2meet_20

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.where2meet_20.databinding.FragmentHomeBinding
import com.parse.ParseQuery
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONObject


class HomeFragment : Fragment() {
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    lateinit var postList : ArrayList<Posts>
    lateinit var adapter: PostAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater);
        val view = fragmentHomeBinding.root
        return view
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postList = ArrayList<Posts>()
        adapter = PostAdapter(postList,requireContext())
        fragmentHomeBinding.rvPosts.adapter = adapter
        fragmentHomeBinding.rvPosts.layoutManager = LinearLayoutManager(requireContext())
        queryPosts()
    }

    private fun queryPosts() {
        val query: ParseQuery<Posts> = ParseQuery.getQuery(Posts::class.java)
        query.include(Posts.KEY_USER)
        query.addDescendingOrder("createdAt")
        query.findInBackground { posts, e ->
            if (e == null) {
                for (post in posts) {
                    Log.i("HomeFragment", "Post: " + post.getDescription())
                }
                postList.addAll(posts)
                adapter.notifyDataSetChanged()
            } else {
                Log.e("HomeFragment", "Issue with getting posts", e)
            }
        }

    }
}
