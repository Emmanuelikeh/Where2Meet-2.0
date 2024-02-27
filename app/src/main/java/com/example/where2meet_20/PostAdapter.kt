package com.example.where2meet_20

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.where2meet_20.databinding.ItemPostBinding

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

        private val itemPostBinding: ItemPostBinding

        init {
            this.itemPostBinding = itemPostBinding
        }

        fun bind(post: Posts) {
            itemPostBinding.tvDescription.text = post.getDescription()
            itemPostBinding.ivImage.load(post.getImage()?.url)
            itemPostBinding.tvUsername.text = post.getUser()?.username
        }
    }



}
