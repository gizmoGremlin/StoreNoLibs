package com.pickledpepper.storenolibs.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.pickledpepper.storenolibs.R
import com.pickledpepper.storenolibs.common.visible
import com.pickledpepper.storenolibs.data.Product
import com.pickledpepper.storenolibs.ui.viewmodels.DetailViewModel
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.product_list_fragment.*

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }
    var detailproductName:TextView? = null
    var detailDescription:TextView? =null
    var detailPrice:TextView? =null
    var detailImage:ImageView? =null
    var detailBundle :Int? = null
    private lateinit var viewModel: DetailViewModel
    var detailProduct: Product? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.loadSingleProduct(detailBundle!!)
        viewModel.getSingleProduct().observe(this, Observer {
            detailProduct=it
            Glide.with(context!!).load(detailProduct?.image).apply(
                RequestOptions.bitmapTransform(
                    RoundedCornersTransformation(8, 5)
                )
            ).into(detailImage!!)
            Log.d("detailFragOnCreateView","detailProduct.name")
            detailproductName?.text = detailProduct?.name
            detailDescription?.text = detailProduct?.description
            detailPrice?.text = detailProduct?.price.toString()
        })
        viewModel.isDataLoading().observe(this, Observer {
            it.let {

                detail_progressBar.visible(it)

            }
        })

        //move this to onActivity creeated


        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBundle = arguments?.getInt("PRODUCT_SKU")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("detailFragOnViewCreated","${detailProduct.toString()}")



        detailproductName =  view?.findViewById<TextView>(R.id.detailname)
        detailDescription = view?.findViewById<TextView>(R.id.detail_desc)
        detailPrice = view?.findViewById<TextView>(R.id.detail_price)










         detailImage = view.findViewById(R.id.detail_image_view)
        var activity = activity as AppCompatActivity
        activity.setSupportActionBar(detail_frag_toolbar as Toolbar)
        activity.supportActionBar?.setElevation(15.0F)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        activity.supportActionBar?.show()



    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        inflater?.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

}
