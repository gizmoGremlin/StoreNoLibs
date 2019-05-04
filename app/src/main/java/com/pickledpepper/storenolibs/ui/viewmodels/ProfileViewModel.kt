package com.pickledpepper.storenolibs.ui.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.pickledpepper.storenolibs.StoreApp

class ProfileViewModel : ViewModel() {

    companion object {
        private var INSTANCE: ProfileViewModel?=null
        private val repository = StoreApp.injectRepository()

        fun getInstance(activity: FragmentActivity): ProfileViewModel? {
            if (INSTANCE == null) {
                INSTANCE = ViewModelProviders.of(activity).get(ProfileViewModel::class.java)
            }
            return INSTANCE
            }
        }

    fun loadUserProfie(uid:String) = repository.loadUserProfile(uid)

    fun getUserProfile() = repository.getSingleUserProfile()

}