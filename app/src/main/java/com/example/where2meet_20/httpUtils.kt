package com.example.where2meet_20

import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler


object httpUtils {
    fun getRequest(hostLink: Any, handler: JsonHttpResponseHandler?) {
        val client = AsyncHttpClient()
        val headers = RequestHeaders()
        headers["Authorization"] = "fsq3RXO1BycINZ5hJz/8sIIjVjWhlFXEQGcLHk4CZq3Ofyk="
        client[hostLink.toString(), headers, null, handler]
    }
};