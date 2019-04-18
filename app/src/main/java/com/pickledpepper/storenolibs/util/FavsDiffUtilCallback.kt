package com.pickledpepper.storenolibs.util

import androidx.recyclerview.widget.DiffUtil
import com.pickledpepper.storenolibs.data.FavProducts

class FavsDiffUtilCallback constructor(private val oldList: List<FavProducts>,
                                       private val updatedList: List<FavProducts>): DiffUtil.Callback() {

    companion object {
        const val NAME_KEY = "name"
        const val ONLINE_STATUS_KEY = "online"
        const val STATUS_KEY = "status"

    }



    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldFavProduct = oldList[oldItemPosition]
            val updatedFavProduct = updatedList[newItemPosition]
          return (oldFavProduct.SKU == updatedFavProduct.SKU) && (oldFavProduct.uId == updatedFavProduct.uId)
    }

    override fun getOldListSize(): Int {
       return oldList.size
    }

    override fun getNewListSize(): Int {
        return  updatedList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavProduct = oldList[oldItemPosition]
        val updatedFavProduct = updatedList[newItemPosition]

        val isNameSame = oldFavProduct.name == updatedFavProduct.name
        val isSameSku = oldFavProduct.SKU == updatedFavProduct.SKU
        val isSameImage = oldFavProduct.image == updatedFavProduct.image
        val isSameUserFav = oldFavProduct.uId == updatedFavProduct.uId

        return isNameSame && isSameSku && isSameImage && isSameUserFav
    }


}