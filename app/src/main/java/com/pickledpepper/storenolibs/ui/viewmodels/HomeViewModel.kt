package com.pickledpepper.storenolibs.ui.viewmodels

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders
import com.pickledpepper.storenolibs.StoreApp
import com.pickledpepper.storenolibs.common.Categories

class HomeViewModel : ViewModel() {
    companion object {
        private var INSTANCE: HomeViewModel? = null
        private val repository = StoreApp.injectRepository()

        fun getInstance(activity: FragmentActivity): HomeViewModel? {
            if (INSTANCE == null) {
                INSTANCE = ViewModelProviders.of(activity).get(
                    HomeViewModel::class.java)
            }
            return INSTANCE
        }
    }

  fun loadCategories() {
      repository.loadCategories()
  }
  fun getCategoryList()=
      repository.getCategoryList()




}
