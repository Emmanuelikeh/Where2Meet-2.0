package com.example.where2meet_20

import android.widget.TextView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DateUtil {
    fun setDate(pattern: String?, textView: TextView, text: String?, date: Date?) {
        var text = text
        if (text == null) {
            text = ""
        }
        val dateFormat: DateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val strDate: String = dateFormat.format(date)
        val presentedDate = text + strDate
        textView.text = presentedDate
    }
}