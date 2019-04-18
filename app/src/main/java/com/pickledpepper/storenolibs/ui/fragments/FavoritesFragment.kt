package com.pickledpepper.storenolibs.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

import com.pickledpepper.storenolibs.R
import com.pickledpepper.storenolibs.adapter.FavListAdapterDiff
import com.pickledpepper.storenolibs.ui.viewmodels.FavoritesViewModel
import kotlinx.android.synthetic.main.favorites_fragment.*

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }
    private lateinit var mAdapter :FavListAdapterDiff
    private lateinit var viewModel: FavoritesViewModel
     var auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        auth.uid?.let { viewModel.loadFavoritesList(it) }
        viewModel.getFavoritesList().observe(this, Observer {
            it.let {
              //  Log.d("FavFragObserver", "${it.toString()}")
               // mAdapter?.submitList(it)
                mAdapter.updateAdapter(it)
            }
        })

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        swipe_refresh_fav_list.setRefreshing(false);
        swipe_refresh_fav_list.setEnabled(false);
        super.onViewCreated(view, savedInstanceState)

        initFavsList()

    }

    private fun initFavsList() {
        mAdapter = context?.let { FavListAdapterDiff(it) }!!.apply {
            onClickAction ={
                view,favProduct ->
                var args = Bundle()
                args.putInt("PRODUCT_SKU", favProduct.SKU)

                val detailFragment = DetailFragment()
                detailFragment.arguments = args

                val fragManager = activity.let {
                    if (it is FragmentActivity)
                        it.supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container,detailFragment,"DETAIL_FRAGMENT")
                            .addToBackStack(null)
                            .commit()
                }

            }
        }

        val mLayoutmanager= LinearLayoutManager(context,RecyclerView.VERTICAL,false)

        fav_list_recycler_view.apply {
            adapter = mAdapter
            setHasFixedSize(true)
            layoutManager= mLayoutmanager
        }

    }

}
