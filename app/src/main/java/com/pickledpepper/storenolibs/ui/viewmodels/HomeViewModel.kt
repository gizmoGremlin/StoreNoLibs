package com.pickledpepper.storenolibs.ui.viewmodels

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders
import com.pickledpepper.storenolibs.common.Categories

class HomeViewModel : ViewModel() {
    companion object {
        private var INSTANCE: HomeViewModel? = null


        fun getInstance(activity: FragmentActivity): HomeViewModel? {
            if (INSTANCE == null) {
                INSTANCE = ViewModelProviders.of(activity).get(
                    HomeViewModel::class.java)
            }
            return INSTANCE
        }
    }

    var liveDataCategory: MutableLiveData<String> = MutableLiveData()

    fun getCategoryForNavigation(): LiveData<String> =liveDataCategory  // observe this in fragment.. and nav onChange
    fun ResetCategoryForNav()= liveDataCategory.postValue(Categories.SWEATSHIRTS.storeCategories)
    fun setCategory( category: String){
        liveDataCategory.postValue(category)
        Log.d("ONCLICK", "Inside Onclick Viewmodel")
    }

}
