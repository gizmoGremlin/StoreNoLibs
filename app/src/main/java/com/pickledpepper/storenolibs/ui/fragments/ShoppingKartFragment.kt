package com.pickledpepper.storenolibs.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth

import com.pickledpepper.storenolibs.R
import com.pickledpepper.storenolibs.ui.viewmodels.ShoppingKartViewModel
import com.pickledpepper.storenolibs.util.reObserve

class ShoppingKartFragment : Fragment() {

    companion object {
        fun newInstance() = ShoppingKartFragment()
    }

    private lateinit var viewModel: ShoppingKartViewModel
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        auth
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shopping_kart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShoppingKartViewModel::class.java)
        auth.uid?.let { viewModel.loadKartList(it) }
        viewModel.getKartList().reObserve(viewLifecycleOwner, Observer {
            it.let {
                ///post to adapter
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ///instanciate adapter recyclerview etc
    }

}
