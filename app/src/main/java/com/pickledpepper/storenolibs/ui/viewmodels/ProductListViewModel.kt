package com.pickledpepper.storenolibs.ui.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders
import com.pickledpepper.storenolibs.StoreApp
import com.pickledpepper.storenolibs.data.FavProducts
import com.pickledpepper.storenolibs.data.Product

class ProductListViewModel : ViewModel() {




    companion object {
        private var INSTANCE: ProductListViewModel? = null

        private val productRepository = StoreApp.injectRepository()

        fun getInstance(activity: FragmentActivity): ProductListViewModel? {
            if (INSTANCE == null) {
                INSTANCE = ViewModelProviders.of(activity).get(ProductListViewModel::class.java)
            }
            return INSTANCE
        }
    }
    fun isDataLoading()=
            productRepository.isDataLoading()
    fun persistSingleItem(product: Product, collection:String){

        productRepository.insertSingleValue(product, collection)

    }
    fun persistProductList(productList:List<Product>, collection: String){

        productRepository.insertListToDb(productList, collection)
    }
    fun getProducts() : LiveData<List<Product>> = productRepository.getProducts()
    fun loadProducts(category: String){

        productRepository.loadProducts(category)

    }
    fun persistFavorite(fav: FavProducts, collection: String){
        productRepository.insertSingleFavorite(fav,collection)
    }
    fun deleteFav(sku: Int,uid:String){
        productRepository.deleteFavorite(sku,uid)
    }

}
