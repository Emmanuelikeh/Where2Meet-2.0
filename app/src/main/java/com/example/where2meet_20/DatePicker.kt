package com.example.where2meet_20

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.Calendar


class DatePicker : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mCalendar = Calendar.getInstance()
        val year = mCalendar[Calendar.YEAR]
        val month = mCalendar[Calendar.MONTH]
        val dayOfMonth = mCalendar[Calendar.DAY_OF_MONTH]
        val dialog =
            DatePickerDialog(requireActivity(), activity as OnDateSetListener?, year, month, dayOfMonth)
        dialog.datePicker.minDate = System.currentTimeMillis() - 1000
        return dialog
    }
}