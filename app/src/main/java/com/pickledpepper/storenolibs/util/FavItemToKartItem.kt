package com.pickledpepper.storenolibs.util

import com.pickledpepper.storenolibs.data.FavProducts
import com.pickledpepper.storenolibs.data.KartProduct

class FavItemToKartItem {


    fun FromFavItemToKartProduct(favItem: FavProducts) :KartProduct{
        return KartProduct(favItem.uId,favItem.SKU,favItem.Category,favItem.name,favItem.description,favItem.price,favItem.image)
    }


}