package com.pickledpepper.storenolibs.ui.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders
import com.pickledpepper.storenolibs.StoreApp

class DetailViewModel : ViewModel() {

    companion object {
        private var INSTANCE: DetailViewModel?=null
        private val productRepository = StoreApp.injectRepository()

        fun getInstance(activity: FragmentActivity): DetailViewModel? {
            if (INSTANCE == null) {
                INSTANCE = ViewModelProviders.of(activity).get(DetailViewModel::class.java)
            }
            return INSTANCE
        }
    }
    fun getSingleProduct()= productRepository.getSingleProduct()
    fun loadSingleProduct(sku:Int){
        productRepository.loadSingleProduct(sku)

    }
    fun isDataLoading()= productRepository.isDataLoading()

}
