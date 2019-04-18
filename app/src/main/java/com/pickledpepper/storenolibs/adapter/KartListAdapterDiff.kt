package com.pickledpepper.storenolibs.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.pickledpepper.storenolibs.data.KartProduct
import com.pickledpepper.storenolibs.ui.viewmodels.ShoppingKartViewModel

class KartListAdapterDiff(context: Context) : RecyclerView.Adapter<KartListAdapterDiff.KartViewHolder>() {

    private var viewModel: ShoppingKartViewModel
    lateinit var user: FirebaseAuth

    init {
        val user = FirebaseAuth.getInstance().currentUser
        viewModel = ViewModelProviders.of(context as FragmentActivity).get(ShoppingKartViewModel::class.java)
    }

    private var favList: MutableList<KartProduct> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KartViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: KartViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }







    inner class KartViewHolder constructor(itemView: View, viewModel: ShoppingKartViewModel): RecyclerView.ViewHolder(itemView){




    }
}