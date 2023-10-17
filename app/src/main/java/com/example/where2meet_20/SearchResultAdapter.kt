package com.example.where2meet_20

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.where2meet_20.databinding.SearchUserItemBinding
import com.parse.ParseUser


class searchResultAdapter(private val parseUserList: ArrayList<ParseUser>,private val context:Context) : RecyclerView.Adapter<searchResultAdapter.ViewHolder>(){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): searchResultAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflate = SearchUserItemBinding.inflate(inflater, parent, false)
        return ViewHolder(inflate);
    }

    override fun onBindViewHolder(holder: searchResultAdapter.ViewHolder, position: Int) {
        val parseUser = parseUserList.get(position)
        holder.bind(parseUser)
    }

    override fun getItemCount(): Int {
       return parseUserList.size
    }

    inner class ViewHolder(searchUserItemBinding: SearchUserItemBinding) :
        RecyclerView.ViewHolder(searchUserItemBinding.root),
        View.OnClickListener {
        private val searchUserItemBinding: SearchUserItemBinding

        init {
            this.searchUserItemBinding = searchUserItemBinding
            itemView.setOnClickListener(this)
        }

        fun bind(parseUser: ParseUser) {
            searchUserItemBinding.tvFriendName.setText(parseUser.username)
            searchUserItemBinding.tvFriendEmail.setText(parseUser.email)
            val image = parseUser.getParseFile("profileImage")
            // check if user has a profile image and sets a default image if not
            searchUserItemBinding.ivFriendImage.load(R.drawable.image_app){
                placeholder(R.drawable.image_app)
            }
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                //transfer selected users information to the parent fragment to continue due process
                val parseUser: ParseUser = parseUserList[position]
                val i = Intent(context,DetailUserActivity::class.java)
                i.putExtra("parseUser", parseUser)
                context.startActivity(i)
            }
        }
    }
    fun clear() {
       parseUserList.clear();
        notifyDataSetChanged();
    }
}