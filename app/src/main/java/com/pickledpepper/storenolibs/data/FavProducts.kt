package com.pickledpepper.storenolibs.data

data class FavProducts (
    var uId:String = "",
    var  SKU : Int = 0,
    var Category : String = "none",
    var name: String = "none",
    var description : String = "none",
    var price : Double = 0.0,
    var image : String = ""
)