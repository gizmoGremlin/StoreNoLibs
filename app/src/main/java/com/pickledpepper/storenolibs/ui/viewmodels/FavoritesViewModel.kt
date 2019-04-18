package com.pickledpepper.storenolibs.ui.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders
import com.pickledpepper.storenolibs.StoreApp
import com.pickledpepper.storenolibs.data.KartProduct

class FavoritesViewModel : ViewModel() {



    companion object {
        private var INSTANCE: FavoritesViewModel?=null
        private val productRepository = StoreApp.injectRepository()

        fun getInstance(activity: FragmentActivity): FavoritesViewModel? {
            if (INSTANCE == null) {
                INSTANCE = ViewModelProviders.of(activity).get(FavoritesViewModel::class.java)
            }
            return INSTANCE
        }
    }
    fun insertSingleKartItem(item:KartProduct){
        productRepository.insertSingleShoppingKartItem(item)
    }
    fun loadFavoritesList(uid:String){
        productRepository.loadFavorites(uid)
    }
    fun getFavoritesList()= productRepository.getFavList()

    fun deleteFav(sku: Int,uid:String){
        productRepository.deleteFavorite(sku,uid)
    }
}
