package com.pickledpepper.storenolibs.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.pickledpepper.storenolibs.R
import com.pickledpepper.storenolibs.data.KartProduct
import com.pickledpepper.storenolibs.ui.viewmodels.ShoppingKartViewModel
import com.pickledpepper.storenolibs.util.KartDiffUtilCallback
import com.pickledpepper.storenolibs.util.round
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class KartListAdapterDiff(context: Context,totalCallBack: GrandTotalCallBack) : RecyclerView.Adapter<KartListAdapterDiff.KartViewHolder>() {

    private var viewModel: ShoppingKartViewModel
    lateinit var user: FirebaseAuth
    var callback = totalCallBack
    init {
        val user = FirebaseAuth.getInstance().currentUser
        viewModel = ViewModelProviders.of(context as FragmentActivity).get(ShoppingKartViewModel::class.java)
    }

    private var kartList: MutableList<KartProduct> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KartViewHolder {
       val layoutInflater = LayoutInflater.from(parent?.context)

        val itemView = layoutInflater.inflate(R.layout.kart_item_view,parent, false)

        return KartViewHolder(itemView,viewModel)
    }

    override fun getItemCount(): Int {
       return kartList.size
    }

    override fun onBindViewHolder(holder: KartViewHolder, position: Int) {
       holder.bind(kartList[position])


    }

    fun updateAdapter(updatedList: List<KartProduct>) {
        val result = DiffUtil.calculateDiff(KartDiffUtilCallback(kartList, updatedList))
        Log.d("InsideDifUpdaeKart","${result}")
        kartList = updatedList.toMutableList()

        result.dispatchUpdatesTo(this)
    }


    fun setAdapterFirstTime(list:List<KartProduct>){
        kartList = list as MutableList<KartProduct>
    }

    inner class KartViewHolder constructor(itemView: View, viewModel: ShoppingKartViewModel): RecyclerView.ViewHolder(itemView){

        val mSpinnerAdapter: SpinnerAdapter

init {

   mSpinnerAdapter = ArrayAdapter.createFromResource(
        itemView.context,
        R.array.quantity_kart_array,
        android.R.layout.simple_spinner_item
    ).apply {
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

}
        val removeButton: ImageButton = itemView.findViewById(R.id.kart_btn_remove)
        var quantitySpinner :Spinner= itemView.findViewById(R.id.kart_item_quantity_spinner)
        val itemTotal = itemView.findViewById<TextView>(R.id.kart_text_view_total_price)
        val kartImage = itemView.findViewById<ImageView>(R.id.kart_item_image)
        val kartTitle = itemView.findViewById<TextView>(R.id.kart_item_title)
        val kartDescription = itemView.findViewById<TextView>(R.id.kart_item_description)
        val kartPrice = itemView.findViewById<TextView>(R.id.kart_item_price)
        val displayQuantity = itemView.findViewById<TextView>(R.id.kart_txt_view_display_quantity)

        fun bind(kartItem:KartProduct){

            if (kartItem.SKU == kartList.get(kartList.size -1).SKU){
                var myTotal = 0.0
                for (item in kartList){
                    myTotal += (item.price * item.quantity)
                }

                callback.getTotal(myTotal.round(2))
            }
            quantitySpinner.adapter = mSpinnerAdapter

            val kartItemImage = kartItem.image
            Glide.with(kartImage.context)
                .load(kartItemImage)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(8, 5)))
                .into(kartImage)
            kartTitle.text = kartItem.name
            kartDescription.text =  kartItem.description
            kartPrice.text = kartItem.price.round(2).toString()
            displayQuantity.text = kartItem.quantity.toString()

            itemTotal.text = (kartItem.quantity * kartItem.price).round(2).toString()

            //set Remove Button listener
            val btnRemoveListener = View.OnClickListener {


                kartList.removeAt(adapterPosition)
                viewModel.deleteKartItem(kartItem.uId,kartItem.SKU)
                // recalculate totals
                var myTotal = 0.0
                for (item in kartList){
                    myTotal += (item.price * item.quantity)
                }

                callback.getTotal(myTotal.round(2))
                myTotal = 0.0
                notifyItemRemoved(adapterPosition)
                notifyItemRangeChanged(adapterPosition,kartList.size)

            }
            removeButton.setOnClickListener(btnRemoveListener)

            quantitySpinner.setSelection(0,false)
            quantitySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("Spinner","Nothing selected: ${parent}")



                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    Log.d("SpinnerQuantity","Item atPosition selected: ${parent?.getItemAtPosition(position)}")
                    Log.d("SpinnerQuantity","KartItem Quantity: ${kartItem.quantity}")
                    kartItem.quantity = Integer.parseInt(parent?.getItemAtPosition(position).toString())
                    Log.d("SpinnerQuantity","KartItem Quantity: ${kartItem.quantity}")
                    displayQuantity.text = kartItem.quantity.toString()
                    itemTotal.text = (kartItem.quantity * kartItem.price).round(2).toString()

                    var myTotal = 0.0
                    for (item in kartList){
                        myTotal += (item.price * item.quantity)
                    }

                    callback.getTotal(myTotal.round(2))
                   myTotal = 0.0
                    notifyItemChanged(position)

                }

            }

        }


    }
}