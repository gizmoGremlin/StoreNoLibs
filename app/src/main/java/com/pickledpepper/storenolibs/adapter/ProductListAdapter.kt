package com.pickledpepper.storenolibs.adapter

import android.view.ViewGroup
import com.pickledpepper.storenolibs.common.RecyclerViewAdapterBase
import com.pickledpepper.storenolibs.common.ViewWrapper
import com.pickledpepper.storenolibs.data.Product

class ProductListAdapter() : RecyclerViewAdapterBase<Product,ProductListItemView>()  {

    var onClickAction :((v : ProductListItemView, product:Product) -> Unit)?= null

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): ProductListItemView =
        ProductListItemView(parent.context)


    override fun onBindViewHolder(holder: ViewWrapper<ProductListItemView>, position: Int) {
      val product= items[position]
        holder.view.apply {
            bindProductListItem(product)
        }
        holder.view.setOnClickListener{
            onClickAction?.invoke(it as @kotlin.ParameterName(name = "v") ProductListItemView,items[position])
        }
    }

}