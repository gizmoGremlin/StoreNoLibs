package com.pickledpepper.storenolibs.ui.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.pickledpepper.storenolibs.StoreApp
import com.pickledpepper.storenolibs.data.UserProfile

class SignInViewModel : ViewModel() {


    companion object {
        private var INSTANCE : SignInViewModel? = null
        private val repository =StoreApp.injectRepository()
        fun getInstance(activity: FragmentActivity): SignInViewModel? {
            if (INSTANCE == null) {
                INSTANCE = ViewModelProviders.of(activity).get(SignInViewModel::class.java)
            }
            return INSTANCE
        }
    }

    fun persistUser(user:UserProfile){
        repository.insertUserProfile(user)
    }
}