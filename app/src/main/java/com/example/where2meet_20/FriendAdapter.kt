package com.example.where2meet_20

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.where2meet_20.databinding.SearchUserItemBinding
import com.parse.ParseUser


class FriendAdapter(private val parseUserList: ArrayList<Followers>, private val context: Context) :  RecyclerView.Adapter<FriendAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflate = SearchUserItemBinding.inflate(inflater, parent, false)
        return ViewHolder(inflate);
    }

    override fun getItemCount(): Int {
       return parseUserList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parseuser = parseUserList.get(position)
        holder.bind(parseuser)
    }

    inner class ViewHolder(inflate: SearchUserItemBinding): RecyclerView.ViewHolder(inflate.root),
        View.OnClickListener  {

        private val inflate:SearchUserItemBinding

        init {
            this.inflate = inflate
            itemView.setOnClickListener(this)
        }

        fun bind(parseuser: Followers) {
            val user = parseuser.getUser()
            if (user != null) {
                 inflate.tvFriendName.text = user.fetchIfNeeded().username.toString()
                inflate.ivFriendImage.load(R.drawable.image_app){
                    placeholder(R.drawable.image_app)
                }
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val result:Followers = parseUserList.get(position)
                val i = Intent()
                i.putExtra("parseUser", result.getUser()?.fetchIfNeeded())
                (context as Activity).setResult(Activity.RESULT_OK, i)
                context.finish()

            }
        }

    }


}
