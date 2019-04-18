package com.pickledpepper.storenolibs.common

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewAdapterBase<T, V:View> : RecyclerView.Adapter<ViewWrapper<V>>() {

    val items = emptyList<T>().toMutableList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewWrapper<V> =
        ViewWrapper(onCreateItemView(parent, viewType))

    override fun getItemCount() = items.size

    abstract fun onCreateItemView(parent: ViewGroup, viewType: Int): V

    open fun setItems(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    open fun addMoreItems(moreItems: List<T>) {
        items.addAll(moreItems)
        notifyDataSetChanged()
    }

    open fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

}