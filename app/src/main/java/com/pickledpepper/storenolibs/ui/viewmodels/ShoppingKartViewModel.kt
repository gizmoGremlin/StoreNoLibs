package com.pickledpepper.storenolibs.ui.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders
import com.pickledpepper.storenolibs.StoreApp

class ShoppingKartViewModel : ViewModel() {

    companion object {
        private var INSTANCE: ShoppingKartViewModel?=null
        private val repository = StoreApp.injectRepository()

        fun getInstance(activity: FragmentActivity): ShoppingKartViewModel? {
            if (INSTANCE == null) {
                INSTANCE = ViewModelProviders.of(activity).get(ShoppingKartViewModel::class.java)
            }
            return INSTANCE
        }
    }
    fun getKartList()=
        repository.getKartList()

    fun loadKartList(uid : String){
        repository.loadListOfKartItems(uid)
    }
    fun deleteKartItem(uid:String,sku:Int)= repository.deleteShoppingKartItem(uid, sku)

}
