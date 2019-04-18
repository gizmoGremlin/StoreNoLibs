package com.pickledpepper.storenolibs.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.pickledpepper.storenolibs.R
import com.pickledpepper.storenolibs.data.FavProducts
import com.pickledpepper.storenolibs.data.Product
import com.pickledpepper.storenolibs.ui.viewmodels.ProductListViewModel
import com.pickledpepper.storenolibs.util.ImageUtil
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.product_list_item_view.view.*

class ProductListItemView constructor(context: Context) : RelativeLayout(context) {
    private  var viewModel: ProductListViewModel
    lateinit var user: FirebaseAuth

    init {
        LayoutInflater.from(context).inflate(R.layout.product_list_item_view, this, true)
        viewModel = ViewModelProviders.of(context as FragmentActivity).get(ProductListViewModel::class.java)
        Log.d("Inside Adapter", viewModel.toString())

    }


    fun bindProductListItem(productItem: Product){
        val image : ImageView = findViewById(R.id.product_image)
        val thisImage = productItem.image
        Glide.with(image.context)
            .load(thisImage)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(8,5)))
            .into(image)

        product_title.text = productItem.name
        val user = FirebaseAuth.getInstance().currentUser
        // cycle through image resources

        var index =0
        val iconClickListner = View.OnClickListener { view ->
           if (index == 0) {
               index++
               product_heart_icon.setImageResource(R.drawable.filled_in_heart_favorite_red_24dp)
               viewModel.persistFavorite(FavProducts(user?.uid?:"", productItem.SKU,productItem.Category,productItem.name,productItem.description,productItem.price,productItem.image ),
                   "favorites")

           }else{
               index--
               product_heart_icon.setImageResource(R.drawable.heart_border_red_24dp)
               viewModel.deleteFav(productItem.SKU,user?.uid?:"")
           }

        }
        product_heart_icon.setOnClickListener(iconClickListner)

    }
}