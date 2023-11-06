package com.example.where2meet_20

import org.json.JSONArray
import org.json.JSONObject


class PlaceSearch {
    var name: String? = null
    var fsqId: String? = null
    var distance = 0
    var iconPrefix: String? = null
    var iconSuffix: String? = null
    var formattedAddress: String? = null
    var address: String? = null


    //constructor
    constructor(jsonObject: JSONObject) {
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
    constructor()

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