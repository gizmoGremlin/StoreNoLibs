package com.pickledpepper.storenolibs.adapter

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.pickledpepper.storenolibs.common.Categories
import com.pickledpepper.storenolibs.data.StoreCategory
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


import androidx.fragment.app.FragmentActivity
import com.pickledpepper.storenolibs.R

import com.pickledpepper.storenolibs.ui.fragments.ProductListFragment
import kotlinx.android.synthetic.main.activity_main.view.*


class HomeListAdapter(context: Context): RecyclerView.Adapter<HomeListAdapter.HomeViewHolder>() {
    var onItemClick: ((StoreCategory) -> Unit)? = null
    private var categoryList : MutableList<StoreCategory> = mutableListOf()
    private var mContext = context

    fun setAdapterList(list:List<StoreCategory>){
        categoryList = list as MutableList<StoreCategory>
        Log.d("HomeInSetAdapterList", list.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)

        val itemView = layoutInflater.inflate(R.layout.home_category_item_view,parent, false)
        Log.d("InHomeListonCreateVH", parent.toString())
        return HomeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return categoryList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        Log.d("InHomeListViewholder", holder.toString())
        holder.bind(categoryList[position])
    }


    inner class HomeViewHolder constructor(itemView:View): RecyclerView.ViewHolder(itemView){
        val sloganTextView = itemView.findViewById<TextView>(R.id.category_slogan)
        val categoryImage :ImageView = itemView.findViewById(R.id.category_image)

        init {
            itemView.setOnClickListener{
                onItemClick?.invoke(categoryList[adapterPosition])
            }
        }
        fun bind(categoryItem:StoreCategory){
            Log.d("InHomeListBind", categoryItem.toString())

           //  val assetManager: AssetManager = mContext.assets
            sloganTextView.text = categoryItem.categorySlogan

          //  val customFont : Typeface = Typeface.createFromAsset(assetManager,"font/dancingscript_bold.ttf")
          //  sloganTextView.setTypeface(customFont)
            val categoryItemImage = categoryItem.categoryImage
            Glide.with(categoryImage.context)
                .load(categoryItemImage)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(8, 5)))
                .into(categoryImage)

//            val imageClickListener = View.OnClickListener {
//                var frag =  ProductListFragment()
//                var myCategoryBundle  = Bundle()
//                myCategoryBundle.putString("CATEGORY", categoryItem.categoryName )
//
//                frag.arguments = myCategoryBundle
//                (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
//                    .replace(com.pickledpepper.storenolibs.R.id.fragment_container, frag)
//                    .commit()
//
//
//
//            }
//            categoryImage.setOnClickListener(imageClickListener)
        }


    }
}