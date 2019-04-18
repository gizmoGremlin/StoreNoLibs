package com.pickledpepper.storenolibs.util

import com.pickledpepper.storenolibs.data.Product

class MapToList {

    fun FromMapToList(myMap: Map<String,Product>): List<Product> {
        var myList = mutableListOf<Product>()
        for (item in myMap)
            myList.add(item.value)

        return myList
    }
}