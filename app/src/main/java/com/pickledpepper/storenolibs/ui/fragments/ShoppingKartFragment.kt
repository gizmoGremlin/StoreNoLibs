package com.pickledpepper.storenolibs.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

import com.pickledpepper.storenolibs.R
import com.pickledpepper.storenolibs.adapter.GrandTotalCallBack
import com.pickledpepper.storenolibs.adapter.KartListAdapterDiff
import com.pickledpepper.storenolibs.data.KartProduct
import com.pickledpepper.storenolibs.ui.viewmodels.ShoppingKartViewModel
import com.pickledpepper.storenolibs.util.SpacesItemDecoration
import com.pickledpepper.storenolibs.util.reObserve
import kotlinx.android.synthetic.main.shopping_kart_fragment.*

class ShoppingKartFragment : Fragment() {

    /*companion object {
        fun newInstance() = ShoppingKartFragment()
    }*/
    private lateinit var kartAdapter: KartListAdapterDiff
    private lateinit var viewModel: ShoppingKartViewModel
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShoppingKartViewModel::class.java)
        auth.uid?.let { viewModel.loadKartList(it) }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.shopping_kart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var totalTextView : TextView = view.findViewById(R.id.kart_price_value)
        kartAdapter = KartListAdapterDiff(this!!.context!!, object :GrandTotalCallBack{

            override fun getTotal(amount: Double) {
                totalTextView.text = amount.toString()
            }
        }


            )
        val myLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        shoppingKart_recyclerview.addItemDecoration(SpacesItemDecoration(4))

        shoppingKart_recyclerview.apply {
            adapter = kartAdapter
            setHasFixedSize(true)
            layoutManager = myLayoutManager
            Log.d("insideOnActivityCreated", "${kartAdapter}")

            viewModel.getKartList().observe(viewLifecycleOwner, Observer {
                it.let {

                    it.let {

                        if(adapter!!.itemCount == 0){
                            Log.d("ObserveGetKartPro" , "list: ${it}")
                            kartAdapter.setAdapterFirstTime(it)
                            (adapter as KartListAdapterDiff).notifyDataSetChanged()
                        }
                        kartAdapter.updateAdapter(it)
                    }
                }
            })

        }

    }
}
