package com.example.where2meet_20

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.where2meet_20.databinding.SearchPlaceItemBinding


class PlaceSearchResultAdapter(private val placeSearchList: ArrayList<PlaceSearch>, private val context: Context) : RecyclerView.Adapter<PlaceSearchResultAdapter.ViewHolder>(){

    //class searchResultAdapter(private val parseUserList: ArrayList<ParseUser>, private val context: Context) : RecyclerView.Adapter<searchResultAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceSearchResultAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflate = SearchPlaceItemBinding.inflate(inflater, parent, false)
        return ViewHolder(inflate);
    }

    override fun onBindViewHolder(holder: PlaceSearchResultAdapter.ViewHolder, position: Int) {
       val placeSearch = placeSearchList.get(position)
        holder.bind(placeSearch)
    }

    override fun getItemCount(): Int {
       return placeSearchList.size
    }

    inner class ViewHolder(searchPlaceItemBinding: SearchPlaceItemBinding): RecyclerView.ViewHolder(searchPlaceItemBinding.root),
        View.OnClickListener{

        private val searchPlaceItemBinding: SearchPlaceItemBinding

        init {
            this.searchPlaceItemBinding = searchPlaceItemBinding
            itemView.setOnClickListener(this)
        }

        fun bind(placeSearch: PlaceSearch) {
            searchPlaceItemBinding.ivName.text = placeSearch.getPlaceName()
            searchPlaceItemBinding.tvAddress.text = placeSearch.getPlaceAddress()
            searchPlaceItemBinding.ivIcon.load(placeSearch.getPlaceIcon()){
                placeholder(R.drawable.image_app)
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                //transfer selected place information to the place detail activity to continue due process
                val placeSearch: PlaceSearch = placeSearchList[position]
                val i = Intent(context,PlaceDetailActivity::class.java)
                i.putExtra("placeSearch",placeSearch)
                context.startActivity(i)
            }
        }

    }

}