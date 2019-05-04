package com.pickledpepper.storenolibs.ui.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth

import com.pickledpepper.storenolibs.R
import com.pickledpepper.storenolibs.ui.viewmodels.DetailViewModel
import com.pickledpepper.storenolibs.ui.viewmodels.ProfileViewModel
import com.pickledpepper.storenolibs.ui.viewmodels.ProfileViewModel.Companion.getInstance
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


class ProfileFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    private var viewmodel : ProfileViewModel? = null



    override fun onCreate(savedInstanceState: Bundle?) {
       auth = FirebaseAuth.getInstance()
        viewmodel = getInstance(activity!!)

        //load user
        auth.uid?.let { viewmodel?.loadUserProfie(it) }
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileName = view.findViewById<TextView>(R.id.profile_name)
        val profileUserName = view.findViewById<TextView>(R.id.profile_user_name)
        val profileEmai = view.findViewById<TextView>(R.id.profile_email)


        viewmodel?.getUserProfile()?.observe(viewLifecycleOwner,Observer {

            profileName.text = it.fullName
            profileUserName.text = it.userName
            profileEmai.text = it.userEmail


        })

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}

