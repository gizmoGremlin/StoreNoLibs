package com.pickledpepper.storenolibs.util

import com.pickledpepper.storenolibs.data.Product

class ListToMap {

    fun FromListToHashMap(list: List<Product>): HashMap<String, Product> {
        var mymap = HashMap<String, Product>()
        for (item in list) mymap.put(item.name, item)

        return mymap
    }

}