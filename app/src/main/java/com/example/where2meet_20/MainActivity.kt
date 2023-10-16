package com.example.where2meet_20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.where2meet_20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainActivity: ActivityMainBinding
    val fragmentManger = supportFragmentManager

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

    }
}