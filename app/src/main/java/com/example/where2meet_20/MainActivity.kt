package com.example.where2meet_20

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.where2meet_20.databinding.ActivityMainBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {
    private lateinit var activityMainActivity: ActivityMainBinding
    val fragmentManger = supportFragmentManager
    val REQUEST_CODE = 100
    var usersLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainActivity.root)

        fragmentManger.beginTransaction().replace(R.id.flContainer,HomeFragment()).commit()
        activityMainActivity.bottomNavigation.setOnItemReselectedListener {  item ->
            var fragmentToShow: Fragment? = null
            when(item.itemId){
                R.id.action_home -> {
                    fragmentToShow = HomeFragment()
                }
                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                }
                R.id.action_search -> {
                    fragmentToShow = SearchFragment()

                }
            }
            if (fragmentToShow != null) {
                fragmentManger.beginTransaction().replace(R.id.flContainer,fragmentToShow).commit()
            }
            true
        }

        getLocation()


    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedLocationProviderClient: Task<Location> =
                LocationServices.getFusedLocationProviderClient(this)
                    .getCurrentLocation(PRIORITY_BALANCED_POWER_ACCURACY, null)
                    .addOnSuccessListener(
                        OnSuccessListener<Location?> { location ->
                            if (location != null) {
                                usersLocation = location
                            } else {
                                Toast.makeText(
                                    this@MainActivity, "Location not found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
        } else {
            askpermission()
        }
    }

    private fun askpermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Location permission is required to use this app",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
    }


}