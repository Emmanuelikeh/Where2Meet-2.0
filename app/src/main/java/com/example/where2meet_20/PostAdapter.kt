package com.example.where2meet_20

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.where2meet_20.databinding.ItemPostBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale


class PostAdapter(private val postList: ArrayList<Posts>, private val context: Context) : RecyclerView.Adapter<PostAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflate = ItemPostBinding.inflate(inflater, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val post = postList.get(position)
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    inner class ViewHolder(itemPostBinding: ItemPostBinding) : RecyclerView.ViewHolder(itemPostBinding.root) {

        private val SECOND_MILLIS = 1000
        private val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private val DAY_MILLIS = 24 * HOUR_MILLIS

        private val itemPostBinding: ItemPostBinding

        init {
            this.itemPostBinding = itemPostBinding
        }

        fun bind(post: Posts) {
            if(post.getDescription() != ""){
                itemPostBinding.tvDescription.text = post.getDescription()
                itemPostBinding.tvDescription.visibility = android.view.View.VISIBLE
            }
            if(post.getImage() == null){
                itemPostBinding.ivPostImage.visibility = android.view.View.GONE
            }
            itemPostBinding.ivPostImage.load(post.getImage()?.url)
            itemPostBinding.tvUsername.text = post.getUser()?.username
            itemPostBinding.ivProfileImage.load(R.drawable.image_app)
            itemPostBinding.tvPostTime.text = getRelativeTimeAgo(post.getCreatedAt().toString())
        }

        fun getRelativeTimeAgo(rawJsonDate: String?): String? {
            //function gets the created at string and modifiies it so it would be displayed
            // in a specific way to the user
            val twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
            val sf = SimpleDateFormat(twitterFormat, Locale.ENGLISH)
            sf.setLenient(true)
            try {
                val time: Long = sf.parse(rawJsonDate).getTime()
                val now = System.currentTimeMillis()
                val diff = now - time
                return if (diff < MINUTE_MILLIS) {
                    "just now"
                } else if (diff < 2 * MINUTE_MILLIS) {
                    "a minute ago"
                } else if (diff < 50 * MINUTE_MILLIS) {
                    (diff / MINUTE_MILLIS).toString() + " m"
                } else if (diff < 90 * MINUTE_MILLIS) {
                    "an hour ago"
                } else if (diff < 24 * HOUR_MILLIS) {
                    (diff / HOUR_MILLIS).toString() + " h"
                } else if (diff < 48 * HOUR_MILLIS) {
                    "yesterday"
                } else {
                    (diff / DAY_MILLIS).toString() + " d"
                }
            } catch (e: ParseException) {
                Log.i("TweetAdapter", "getRelativeTimeAgo failed")
                e.printStackTrace()
            }
            return ""
        }

    }



}
