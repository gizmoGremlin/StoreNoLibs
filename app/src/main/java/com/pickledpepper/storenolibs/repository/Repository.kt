package com.pickledpepper.storenolibs.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.pickledpepper.storenolibs.common.Categories
import com.pickledpepper.storenolibs.data.FavProducts
import com.pickledpepper.storenolibs.data.KartProduct
import com.pickledpepper.storenolibs.data.Product
import com.pickledpepper.storenolibs.util.ListToMap

class Repository (val db: FirebaseFirestore) {

    // add a single document
    var lastResultArrayPolo: ArrayList<DocumentSnapshot> = arrayListOf()
    var lastResultArraySweatShirt: ArrayList<DocumentSnapshot> = arrayListOf()
    var lastResultArrayBags: ArrayList<DocumentSnapshot> = arrayListOf()
    var lastResultArrayPants: ArrayList<DocumentSnapshot> = arrayListOf()
    var lastResultArrayBackpacks: ArrayList<DocumentSnapshot> = arrayListOf()
    var lastResultArraySocks: ArrayList<DocumentSnapshot> = arrayListOf()
    private var kartList:MutableLiveData<List<KartProduct>> = MutableLiveData()
    private var favoritesList: MutableLiveData<List<FavProducts>> = MutableLiveData()
    private var singleProduct: MutableLiveData<Product> = MutableLiveData()
    private var isDataLoading: MutableLiveData<Boolean> = MutableLiveData()
    private var products: MutableLiveData<List<Product>> = MutableLiveData()
    fun getKartList(): LiveData<List<KartProduct>> = kartList
    fun getFavList():LiveData<List<FavProducts>> = favoritesList
    fun getProducts(): LiveData<List<Product>> = products
    fun getSingleProduct():LiveData<Product> = singleProduct
    fun isDataLoading() = isDataLoading


    fun insertSingleValue(product: Product, collection: String) {

        db.collection(collection)
            .add(product)
            .addOnSuccessListener { documentReference ->
                Log.d("repo", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("repo", "Error adding document", e)
            }

    }
    fun loadSingleProduct(sku: Int) {
        isDataLoading.postValue(true)

            val docRef =db.collection("productList").whereEqualTo("sku", sku)

                docRef.get()
                .addOnSuccessListener { result ->
                   if (result != null){
                       var counter = 0
                       for (doc in result)
                           if (counter == 0) {
                               singleProduct.postValue(doc.toObject(Product::class.java))
                               isDataLoading.postValue(false)
                               Log.d("inLoadSingleProduct","singleProduct:${(doc.toObject(Product::class.java))}")
                           }else{
                               break
                           }
                   }else{
                       Log.d("inLoadSingleProduct","error")

                   }
                }.addOnFailureListener {
                        e ->
                        Log.d("inLoadSingleProduct","error")
                    }

    }

    fun insertSingleShoppingKartItem(kartItem: KartProduct){
        db.collection("ShoppingKart")
            .add(kartItem)
            .addOnSuccessListener { documentReference ->
                Log.d("repo:Kart", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("repo:Kart", "Error adding document", e)
            }

    }

    fun insertSingleFavorite(fav: FavProducts, collection: String) {
        db.collection(collection)
            .add(fav)
            .addOnSuccessListener { documentReference ->
                Log.d("repo:favs", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("repo", "Error adding document", e)
            }
    }
        fun insertListToDb(productList: List<Product>, collection: String) {
            var productListCollRef = db.collection("productList")

            for (item in productList)
                productListCollRef.add(item)
            db.collection("productList")
                .add(ListToMap().FromListToHashMap(productList))
                .addOnSuccessListener { documentReference ->
                    Log.d("repo_List", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("repo", "Error adding document", e)

                }
        }
    fun loadListOfKartItems(uid: String){
        isDataLoading.postValue(true)
        val getKartList = db.collection("ShoppingKart").whereEqualTo("uid", uid ).orderBy("price").limit(40)

        getKartList.get().addOnSuccessListener {
            querySnapshot ->
            var myKartList = mutableListOf<KartProduct>()
            for (doc in querySnapshot){
                var kartItem = KartProduct(quantity = 1)
                kartItem = doc.toObject(KartProduct::class.java)
                myKartList.add(kartItem)
            }
            Log.w("load Kart List", "KartList: ${myKartList} ")
            kartList.postValue(myKartList)
            isDataLoading.postValue(false)
        }
            .addOnFailureListener { exception ->
                Log.w("error", "Error getting documents: ", exception)
                isDataLoading.postValue(false)
            }


    }
    fun loadFavorites(uid: String){
        isDataLoading.postValue(true)
        val getFavs =db.collection("favorites").whereEqualTo("uid", uid)
        getFavs.get().addOnSuccessListener {querySnapshot->
            var myFavList = mutableListOf<FavProducts>()
            for (document in querySnapshot ){
                var fav= FavProducts()
                    fav = document.toObject(FavProducts::class.java)
                myFavList.add(fav)
            }
            favoritesList.postValue(myFavList)
            isDataLoading.postValue(false)
        }
            .addOnFailureListener {exception ->
                Log.w("error", "Error getting documents: ", exception)

            }
    }

        fun loadProducts(category: String) {
            //Set result array based on Category

            var lastResultArrayBasedOnCategory: ArrayList<DocumentSnapshot> = arrayListOf()
            var myCategory = String()
            when(category){
                Categories.SWEATSHIRTS.storeCategories ->{
                    lastResultArrayBasedOnCategory =lastResultArraySweatShirt
                    myCategory = Categories.SWEATSHIRTS.storeCategories
                }
                Categories.SOCKS.storeCategories -> {
                    lastResultArrayBasedOnCategory = lastResultArraySocks
                    myCategory = Categories.SOCKS.storeCategories
                }
                Categories.PANTS.storeCategories -> {
                    lastResultArrayBasedOnCategory = lastResultArrayPants
                    myCategory = Categories.PANTS.storeCategories
                }
                Categories.BACKPACKS.storeCategories -> {
                    lastResultArrayBasedOnCategory =  lastResultArrayBackpacks
                    myCategory = Categories.BACKPACKS.storeCategories
                }
                Categories.POLOS.storeCategories ->{
                    lastResultArrayBasedOnCategory = lastResultArrayPolo
                    myCategory = Categories.POLOS.storeCategories
                }
                Categories.BAGS.storeCategories -> {
                    lastResultArrayBasedOnCategory =  lastResultArrayBags
                    myCategory = Categories.BAGS.storeCategories
                }
            }

            isDataLoading.postValue(true)
            Log.d("LoadProductsCategoryAf", "Category ${myCategory}")
            if(lastResultArrayBasedOnCategory.size ==0){
            val getProducts = db.collection("productList")
                .whereEqualTo("category", category)
                .orderBy("sku")
                .limit(10)
                .get()
                .addOnSuccessListener { result ->
                    var myList = mutableListOf<Product>()
                    Log.d("ResultArray", "result: ${result.size()}")
                    for (documents in result) {

                        when(myCategory){
                            Categories.SWEATSHIRTS.storeCategories ->{
                             lastResultArraySweatShirt.add(result.documents.last())
                            }
                            Categories.SOCKS.storeCategories -> {
                                lastResultArraySocks.add(result.documents.last())
                            }
                            Categories.PANTS.storeCategories -> {
                                lastResultArrayPants.add(result.documents.last())
                            }
                            Categories.BACKPACKS.storeCategories -> {
                                lastResultArrayBackpacks.add(result.documents.last())
                            }
                            Categories.POLOS.storeCategories ->{
                                lastResultArrayPolo.add(result.documents.last())
                            }
                            Categories.BAGS.storeCategories -> {
                                lastResultArrayBags.add(result.documents.last())
                            }
                        }
                       // lastResultArrayBasedOnCategory.add(result.documents.last())
                        Log.d("ResultArray", "size: ${lastResultArrayBasedOnCategory.size}")
                        var pro = Product()
                        pro = documents.toObject(Product::class.java)
                        myList.add(pro)
                    }


                    products.postValue(myList)
                    Log.d("InsideLoadProducts", "DocumentSnapshot added with ID: ${result.metadata}")
                    isDataLoading.postValue(false)
                }

                .addOnFailureListener { exception ->
                    Log.d("error", "Error getting documents: ", exception)
                }
            }else{
                Log.d("InsideGetMoreElse", "Else Query Fired")
                isDataLoading.postValue(true)
                val getMoreProducts = db.collection("productList")
                    .whereEqualTo("category", category)
                    .orderBy("sku")
                    .startAfter(lastResultArrayBasedOnCategory.get(lastResultArrayBasedOnCategory.size - 1))
                    .limit(10)
                    .get()
                    .addOnSuccessListener { result ->
                        var myList = mutableListOf<Product>()
                        Log.d("InsideGetMoreSucc", "DocumentSnapshot added with ID: ${result.toString()}")
                        for (documents in result) {

                            when(myCategory){
                                Categories.SWEATSHIRTS.storeCategories ->{
                                    lastResultArraySweatShirt.add(result.documents.last())
                                }
                                Categories.SOCKS.storeCategories -> {
                                    lastResultArraySocks.add(result.documents.last())
                                }
                                Categories.PANTS.storeCategories -> {
                                    lastResultArrayPants.add(result.documents.last())
                                }
                                Categories.BACKPACKS.storeCategories -> {
                                    lastResultArrayBackpacks.add(result.documents.last())
                                }
                                Categories.POLOS.storeCategories ->{
                                    lastResultArrayPolo.add(result.documents.last())
                                }
                                Categories.BAGS.storeCategories -> {
                                    lastResultArrayBags.add(result.documents.last())
                                }
                            }
                            Log.d("InsideGetMoreSucc", "DocumentSnapshot added with ID: ${result.toString()}")
                            //lastResultArrayBasedOnCategory.add(result.documents.last())
                            var pro = Product()
                            pro = documents.toObject(Product::class.java)
                            myList.add(pro)
                        }
                        Log.d("InsideGetMoreProd", "DocumentSnapshot added with ID: ${myList.toString()}")
                        ////////Conver this to a list!!
                        products.postValue(myList)
                        Log.d("InsideGetMoreProd", "DocumentSnapshot added with ID: ${result.metadata}")
                        isDataLoading.postValue(false)
                    }.addOnFailureListener {
                        e ->  Log.d("errorInGetMoreProducts", "Error getting documents: " +e)
                    }



            }

        }
    fun deleteFavorite(sku: Int,uid:String) {
        var favQuery = db.collection("favorites")
            .whereEqualTo("sku", sku)
            .whereEqualTo("uid", uid)
            .get().addOnCompleteListener({ task ->
                if (task.isSuccessful) {
                    val result =  task.result
                    Log.d("Repo Delete Favorite", "Before task deleted: ${task.result}")
                    for (item in result!!)

                        db.collection("favorites").document(item.id).delete()
                        Log.d("Repo Delete Favorite", "task deleted: ${task.result}")
                    }
                })

            }



    }





