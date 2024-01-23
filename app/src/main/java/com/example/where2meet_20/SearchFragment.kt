package com.example.where2meet_20

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.where2meet_20.databinding.FragmentSearchBinding


class SearchFragment : Fragment(){
    private lateinit var fragmentSearchBinding: FragmentSearchBinding
    private val model: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater,container,false)
        val view = fragmentSearchBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSearchBinding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                model.searchQuery.value = query
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        addFragment();
    }

    private fun addFragment() {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(PlaceSearchFragment(this),"Places")
        adapter.addFragment(AccountSearchFragment(),"Accounts")
        fragmentSearchBinding.viewPager.adapter = adapter;
        fragmentSearchBinding.tabLayout.setupWithViewPager(fragmentSearchBinding.viewPager);
    }


}