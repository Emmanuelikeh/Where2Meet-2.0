package com.example.where2meet_20

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    val searchQuery = MutableLiveData<String>()

}