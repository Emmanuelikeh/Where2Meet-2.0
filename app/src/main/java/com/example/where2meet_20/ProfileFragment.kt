package com.example.where2meet_20

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.where2meet_20.databinding.FragmentProfileBinding
import com.parse.ParseQuery
import com.parse.ParseUser


class ProfileFragment : Fragment() {
    private lateinit var fragmentProfileBinding: FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater);
        var view = fragmentProfileBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentUserInformation()
        fragmentProfileBinding.btnEditProfile.setOnClickListener{
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getCurrentUserInformation() {
        var currentUser = ParseUser.getCurrentUser()
        val image = currentUser.getParseFile("profileImage")
        val biography = currentUser.getString("userBiography")
        fragmentProfileBinding.tvUsername.text = currentUser.username
        fragmentProfileBinding.tvUserBio.text = biography
        fragmentProfileBinding.ivUserProfileImage.load(R.drawable.image_app){
            placeholder(R.drawable.image_app)
        }

        // get the following count
        val followingCount = currentUser.getInt("followingCount")
        fragmentProfileBinding.tvFollowingCount.text = followingCount.toString()
        // get the follower count
        val followerCount = currentUser.getInt("followerCount")
        getFollowersCount(currentUser)
        getFollowingCount(currentUser)
    }

    // get the following count
    private fun getFollowingCount(parseUser: ParseUser){
        val query = ParseQuery.getQuery(Followers::class.java)
        query.whereEqualTo("Follower", parseUser)
        query.countInBackground { count, e ->
            if (e == null){
                fragmentProfileBinding.tvFollowingCount.text = count.toString()
            }
        }

    }
    // get the follower count
    private fun getFollowersCount(parseUser: ParseUser){
        val query = ParseQuery.getQuery(Followers::class.java)
        query.whereEqualTo("Following", parseUser)
        query.findInBackground() { count, e ->
            if (e == null){
                fragmentProfileBinding.tvFollowerCount.text = count.size.toString()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getCurrentUserInformation()
    }


}