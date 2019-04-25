package com.pickledpepper.storenolibs.util

import androidx.recyclerview.widget.DiffUtil
import com.pickledpepper.storenolibs.data.KartProduct

class KartDiffUtilCallback constructor(private val oldList: List<KartProduct>,
                                       private val updatedList: List<KartProduct>):DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldKartItem = oldList[oldItemPosition]
        val updatedKartItem = updatedList[newItemPosition]
        return (oldKartItem.SKU == updatedKartItem.SKU) && (oldKartItem.uId == updatedKartItem.uId)
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
       return updatedList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldKartItem = oldList[oldItemPosition]
        val updatedKartItem = updatedList[newItemPosition]

        val isNameSame = oldKartItem.name == updatedKartItem.name
        val isSameSku = oldKartItem.SKU == updatedKartItem.SKU
        val isSameImage = oldKartItem.image == updatedKartItem.image
        val isSameUserFav = oldKartItem.uId == updatedKartItem.uId

        return isNameSame && isSameSku && isSameImage && isSameUserFav
    }
}