package com.pickledpepper.storenolibs.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AbsListView
import android.widget.AbsListView.OnScrollListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.pickledpepper.storenolibs.ui.viewmodels.ProductListViewModel
import com.pickledpepper.storenolibs.R
import com.pickledpepper.storenolibs.adapter.ProductListAdapter
import com.pickledpepper.storenolibs.common.Categories
import com.pickledpepper.storenolibs.data.Product
import kotlinx.android.synthetic.main.product_list_fragment.*
import kotlin.random.Random

import com.pickledpepper.storenolibs.common.visible
import com.pickledpepper.storenolibs.util.SpacesItemDecoration

import androidx.recyclerview.widget.LinearLayoutManager
import com.pickledpepper.storenolibs.MainActivity
import com.pickledpepper.storenolibs.util.reObserve


class ProductListFragment : Fragment() {

  /*  companion object {
        fun newInstance() = ProductListFragment()
    }*/
    var theBundle = String()
    private lateinit var viewModel: ProductListViewModel
    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val rootView = inflater.inflate(com.pickledpepper.storenolibs.R.layout.product_list_fragment,container,false)
        setHasOptionsMenu(true);


        return  rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        swipe_refresh_product_list.setRefreshing(false);
        swipe_refresh_product_list.setEnabled(false);
        super.onViewCreated(view, savedInstanceState)




        theBundle  =arguments?.get("CATEGORY") as String


        initProductList()


      //  val testTextview = view.findViewById(R.id.) as TextView
    //    testTextview.text =theBundle.toString()

    }

    private fun showProductList(productList:List<Product>) {
        if (productListAdapter.items.isEmpty()){
            productListAdapter.setItems(productList)
            Log.d("ZZZOVCSHOW",productList.toString())

        }else{
            Log.d("ZZZOVCMORE",productList.toString())
            productListAdapter.addMoreItems(productList)
        }
    }

    private fun initProductList() {
        productListAdapter = ProductListAdapter().apply {
            onClickAction = {
                view, product -> // do fragment transaction--- pass product sku//id in args fro9m product
                var args = Bundle()
                args.putInt("PRODUCT_SKU", product.SKU)

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

        val mLayoutManager = GridLayoutManager(
            context, 2,RecyclerView.VERTICAL,false)

      /*  val horizontalDivider=ContextCompat.getDrawable(context!!, R.drawable.divider_medium)
        val verticalDivider=ContextCompat.getDrawable(context!!, R.drawable.divider_vertical)
        product_list_recycler_view.addItemDecoration(GridDividerItemDecoration(horizontalDivider,verticalDivider,2))*/
       product_list_recycler_view.addItemDecoration(SpacesItemDecoration(4))

        val scrollListener = object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
               // if (isLastItemDisplaying(recyclerView)) {

                if (isLastVisible()) {
                    viewModel.loadProducts(theBundle)
                    Log.d("ZZZOVC" , theBundle)

                }
            }
        }

        product_list_recycler_view.addOnScrollListener(scrollListener)

        product_list_recycler_view.apply {
                adapter = productListAdapter

            setHasFixedSize(true)
            layoutManager= mLayoutManager
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
      //  (activity as MainActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
       // (activity as MainActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        var activity = activity as AppCompatActivity
        activity.setSupportActionBar(frag_toolbar as Toolbar)
        activity.supportActionBar?.setElevation(15.0F)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_reorder_black_24dp)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        activity.supportActionBar?.show()



        viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.loadProducts(theBundle)

        viewModel.getProducts().reObserve(viewLifecycleOwner, Observer {
            it.let {
                if(it.first().Category == theBundle) {
                    showProductList(it)
                }
            }
        })

    }


    override fun onPause() {
        super.onPause()




    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {


        inflater?.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         when(item?.itemId){
            R.id.shoppingkart_menu -> {
                activity.let {
                    if (it is FragmentActivity){
                        it.supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container,ShoppingKartFragment(), "ShoppingKart_Fragment")
                            .addToBackStack(null)
                            .commit()
                    }
                }


                return true
            }
            R.id.favorites_menu -> {
                 activity.let {
                    if (it is FragmentActivity)
                        it.supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container,FavoritesFragment(),"Favorites_FRAGMENT")
                            .addToBackStack(null)
                            .commit()

                }

                return true
            }
             android.R.id.home -> {
                 (activity as MainActivity).openDrawer()

                 return true
             }


            }

        return super.onOptionsItemSelected(item)
    }

    fun isLastVisible(): Boolean {
        val layoutManager = product_list_recycler_view.getLayoutManager() as GridLayoutManager
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        val numItems = product_list_recycler_view.getAdapter()?.getItemCount()

        if (numItems != null)return pos >= numItems-1 else return false

    }

    override fun onResume() {
        super.onResume()

    }


}

