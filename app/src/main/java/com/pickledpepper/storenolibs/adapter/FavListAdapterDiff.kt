package com.pickledpepper.storenolibs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.pickledpepper.storenolibs.R
import com.pickledpepper.storenolibs.data.FavProducts
import com.pickledpepper.storenolibs.data.Product
import com.pickledpepper.storenolibs.ui.viewmodels.FavoritesViewModel
import com.pickledpepper.storenolibs.util.FavItemToKartItem
import com.pickledpepper.storenolibs.util.FavsDiffUtilCallback
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/*

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.pickledpepper.storenolibs.R
import com.pickledpepper.storenolibs.data.FavProducts
import com.pickledpepper.storenolibs.ui.viewmodels.FavoritesViewModel
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class FavListAdapterDiff(context: Context) : ListAdapter<FavProducts, FavListAdapterDiff.ItemViewholder>(DiffCallback()) {


    lateinit var mClickListener: ClickListener
    private  var viewModel: FavoritesViewModel
    lateinit var user: FirebaseAuth
    init {
        val user = FirebaseAuth.getInstance().currentUser
        viewModel = ViewModelProviders.of(context as FragmentActivity).get(FavoritesViewModel::class.java)
    }
    interface ClickListener {
        fun onClick(pos: Int, aView: View)
    }
    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(viewModel,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favs_list_item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavListAdapterDiff.ItemViewholder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class ItemViewholder(viewModel:FavoritesViewModel,itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        override fun onClick(v: View?) {
            mClickListener.onClick(adapterPosition, itemView)
        }

        val viewModel = viewModel

        fun bind(item: FavProducts) = with(itemView) {
            val removeFavBtn : ImageButton = findViewById(R.id.btn_remove)
            val image : ImageView = findViewById(R.id.fav_listview_image)
            val favImageInData = item.image
            Glide.with(image.context)
                .load(favImageInData)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(8,5)))
                .into(image)

            val user = FirebaseAuth.getInstance().currentUser
            val txtName: TextView = findViewById(R.id.fav_txtview_name)
            val txtPrice: TextView = findViewById(R.id.fav_txtview_price)

            txtName.text = item.name
            txtPrice.text = item.price.toString()


            val btnRemoveListener = View.OnClickListener {

                viewModel.deleteFav(item.SKU,user?.uid?:"")
                notifyDataSetChanged()



            }

            removeFavBtn.setOnClickListener(btnRemoveListener)



        }
    }
}



class DiffCallback : DiffUtil.ItemCallback<FavProducts>() {



    override fun areItemsTheSame(oldItem: FavProducts, newItem: FavProducts): Boolean {
        return oldItem?.SKU == newItem?.SKU && oldItem.uId == newItem.uId
    }

    override fun areContentsTheSame(oldItem: FavProducts, newItem: FavProducts): Boolean {
        return oldItem == newItem
    }
}*/
class FavListAdapterDiff(context: Context) : RecyclerView.Adapter<FavListAdapterDiff.FavViewHolder>() {

    var onClickAction :((v:View  , product: FavProducts) -> Unit)?= null
    private var viewModel: FavoritesViewModel
    lateinit var user: FirebaseAuth

    init {
        val user = FirebaseAuth.getInstance().currentUser
        viewModel = ViewModelProviders.of(context as FragmentActivity).get(FavoritesViewModel::class.java)
    }

    private var favList: MutableList<FavProducts> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)

        val itemView = layoutInflater.inflate(R.layout.favs_list_item_view, parent, false)
        return FavViewHolder(itemView, viewModel)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(favList[position])
        holder.itemView.setOnClickListener {
            onClickAction?.invoke(it , favList[position] )
        }

    }

    fun updateAdapter(updatedList: List<FavProducts>) {
        val result = DiffUtil.calculateDiff(FavsDiffUtilCallback(favList, updatedList))

        favList = updatedList.toMutableList()

        result.dispatchUpdatesTo(this)
    }


    inner class FavViewHolder constructor(itemView: View, viewModel: FavoritesViewModel) :
        RecyclerView.ViewHolder(itemView) {
        val viewModel = viewModel
        val removeFavBtn: ImageButton = itemView.findViewById(R.id.btn_remove)
        val image: ImageView = itemView.findViewById(R.id.fav_listview_image)
        val txtName: TextView = itemView.findViewById(R.id.fav_txtview_name)
        val txtPrice: TextView = itemView.findViewById(R.id.fav_txtview_price)
        val btnMoveToKart :Button = itemView.findViewById(R.id.fav_checkout)

        fun bind(favItem: FavProducts) {
            val user = FirebaseAuth.getInstance().currentUser
            val favImageInData = favItem.image
            Glide.with(image.context)
                .load(favImageInData)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(8, 5)))
                .into(image)
            txtName.text = favItem.name
            txtPrice.text = favItem.price.toString()

            val btnMoveToKartListener = View.OnClickListener {
                var shoppingKartItem = FavItemToKartItem().FromFavItemToKartProduct(favItem)
                viewModel.insertSingleKartItem(shoppingKartItem)
            }
            btnMoveToKart.setOnClickListener(btnMoveToKartListener)

            val btnRemoveListener = View.OnClickListener {

                viewModel.deleteFav(favItem.SKU, user?.uid ?: "")
                favList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                notifyItemRangeChanged(adapterPosition,favList.size)

            }

            removeFavBtn.setOnClickListener(btnRemoveListener)


        }

    }

}

