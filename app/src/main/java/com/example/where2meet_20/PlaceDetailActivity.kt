package com.example.where2meet_20

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.where2meet_20.databinding.ActivityPlaceDetailBinding
import com.example.where2meet_20.httpUtils.getRequest
import com.parse.ParseUser
import okhttp3.Headers
import java.text.DateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class PlaceDetailActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener{
    private lateinit var activityPlaceDetailBinding: ActivityPlaceDetailBinding
    private var hour = 0
    private var minute = 0
    private val REQUEST_CODE = 20
    private val receiversList: ArrayList<ParseUser> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPlaceDetailBinding = ActivityPlaceDetailBinding.inflate(layoutInflater)
        setContentView(activityPlaceDetailBinding.root)
        val placeSearch = intent.getParcelableExtra("placeSearch") as PlaceSearch?
        activityPlaceDetailBinding.tvDetailName.text = placeSearch?.getPlaceName()
        activityPlaceDetailBinding.tvFormattedAddress.text = placeSearch?.getPlaceAddress()
        var hostLink = getHostLink(placeSearch?.getPlaceFsqId())
        queryPlaceImage(hostLink)

        activityPlaceDetailBinding.btnSelectFriend.setOnClickListener{
            val i = Intent(this, FriendsActivity::class.java)
            startActivityForResult(i,REQUEST_CODE)

        }

        activityPlaceDetailBinding.btnSend.setOnClickListener{
            invitechecker()
        }
    }

    private fun invitechecker() {
        if(receiversList.size == 0){
            return;
        }
        if(activityPlaceDetailBinding.tvComposeDate.getText().toString().equals("")){
            return;
        }
        if(activityPlaceDetailBinding.tvComposeTime.getText().toString().equals("")){
            return;
        }
        querySend()
    }

    private fun querySend() {
        val inviteDate: Date = getDateAndTime()
        val title = "Invitation to ${activityPlaceDetailBinding.tvDetailName.text}"
        val description = "You are invited to ${activityPlaceDetailBinding.tvDetailName.text} at ${activityPlaceDetailBinding.tvComposeTime.text} on ${activityPlaceDetailBinding.tvComposeDate.text}"
        val address = activityPlaceDetailBinding.tvFormattedAddress.text.toString()
        val receiver = receiversList[0]
        val sender = ParseUser.getCurrentUser()
        val invite = Invite()
        invite.title = title
        invite.flag = 0
        invite.invitationDate = inviteDate
        invite.address = address
        invite.sender = sender
        invite.receiver = receiver
        invite.saveInBackground { e ->
            if (e == null) {
                Toast.makeText(this, "Invite sent", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Log.i("PlaceDetailActivity", "querySend: Invite failed")
            }
        }


    }

    private fun getDateAndTime(): Date {
        val date = activityPlaceDetailBinding.tvComposeDate.text.toString()
        val time = activityPlaceDetailBinding.tvComposeTime.text.toString()
        val dateWithTime = date + " " + time
        val dateFormat = DateFormat.getDateInstance(DateFormat.FULL)
        return dateFormat.parse(dateWithTime)
    }

    private fun queryPlaceImage(hostLink:String) {
        Log.i("PlaceDetailActivity", "queryPlaceImage: $hostLink")
        getRequest(hostLink, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i("PlaceDetailActivity", "onSuccess: $json")
                val jsonArray = json.jsonArray
                    val firstItem = jsonArray.getJSONObject(0)
                    val imagePrefix = firstItem.getString("prefix")
                    val imageSuffix = firstItem.getString("suffix")
                    activityPlaceDetailBinding.ivPlaceImage.load(imagePrefix + "original" + imageSuffix)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers,
                response: String,
                throwable: Throwable
            ) {
                Log.i("PlaceDetailActivity", "onFailure: $response")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
                val user = data?.getParcelableExtra<ParseUser>("parseUser")
                activityPlaceDetailBinding.tvSelectFriend.text = user?.username

            if(receiversList.size < 1){
                receiversList.add(0,user!!)
            }
            else{
                receiversList.add(0,user!!)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getHostLink(placeId: String?): String {
        return "https://api.foursquare.com/v3/places/$placeId/photos"
    }

    fun popTimePicker(view: View) {
        val onTimeSetListener =
            OnTimeSetListener { view, selectedHour, selectedMinute ->
                hour = selectedHour
                minute = selectedMinute
                activityPlaceDetailBinding.tvComposeTime.text = java.lang.String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    hour,
                    minute
                )
            }
        val timePickerDialog = TimePickerDialog(this, onTimeSetListener, hour, minute, true)
        timePickerDialog.setTitle("Select Time")
        timePickerDialog.show()
    }
    fun popDatePicker(view: View) {
        val datePicker: DatePicker = DatePicker()
        datePicker.show(supportFragmentManager, "Date pick")
    }
    override fun onDateSet(
        view: android.widget.DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        val mCalendar: Calendar = Calendar.getInstance()
        mCalendar.set(Calendar.YEAR, year)
        mCalendar.set(Calendar.MONTH, month)
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val selectedDate: String =
            DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime())
        activityPlaceDetailBinding.tvComposeDate.text = selectedDate
    }

}