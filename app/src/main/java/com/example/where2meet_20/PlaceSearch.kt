package com.example.where2meet_20

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
import org.json.JSONObject

@Parcelize
class PlaceSearch(
    var name: String? = null,
    var fsqId: String? = null,
    var distance: Int = 0,
    var iconPrefix: String? = null,
    var iconSuffix: String? = null,
    var formattedAddress: String? = null,
    var address: String? = null
) : Parcelable {

    constructor(jsonObject: JSONObject) : this() {
        this.name = jsonObject.getString("name")
        this.fsqId = jsonObject.getString("fsq_id")
        this.distance = jsonObject.getInt("distance")
        val categories: JSONArray = jsonObject.getJSONArray("categories")
        val firstItem = categories.getJSONObject(0)
        val icon = firstItem.getJSONObject("icon")
        this.iconPrefix = icon.getString("prefix")
        this.iconSuffix = icon.getString("suffix")
        val location: JSONObject = jsonObject.getJSONObject("location")
        this.formattedAddress = location.getString("formatted_address")
        this.address = location.getString("address")
    }

    companion object : Parceler<PlaceSearch> {

        override fun PlaceSearch.write(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(fsqId)
            parcel.writeInt(distance)
            parcel.writeString(iconPrefix)
            parcel.writeString(iconSuffix)
            parcel.writeString(formattedAddress)
            parcel.writeString(address)
        }

        override fun create(parcel: Parcel): PlaceSearch {
            return PlaceSearch(parcel)
        }

        fun fromJsonArray(results: JSONArray?): Collection<PlaceSearch> {
            val placeSearchList = ArrayList<PlaceSearch>()
            for (i in 0 until (results?.length() ?: 0)) {
                val placeSearch = results?.getJSONObject(i)?.let { PlaceSearch(it) }
                if (placeSearch != null) {
                    placeSearchList.add(placeSearch)
                }
            }
            return placeSearchList
        }
    }

    private constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    fun getPlaceName(): String? {
        return name
    }

    fun getPlaceFsqId(): String? {
        return fsqId
    }

    fun getPlaceFormattedAddress(): String? {
        return formattedAddress
    }

    fun getPlaceAddress(): String? {
        return address
    }

    fun getPlaceIcon(): String? {
        return iconPrefix + 120 + iconSuffix
    }

    fun getPlaceDistance(): String? {
        return "$distance meters"
    }


}
