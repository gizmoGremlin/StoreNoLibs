package com.pickledpepper.storenolibs.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
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

        detailBundle = arguments?.getInt("PRODUCT_SKU")
        viewModel.loadSingleProduct(detailBundle!!)
        viewModel.getSingleProduct().observe(this, Observer {
            detailProduct=it
            Glide.with(context!!).load(detailProduct?.image).apply(
                RequestOptions.bitmapTransform(
                    RoundedCornersTransformation(8, 5)
                )
            ).into(detailImage!!)
            Log.d("detailFragOnCreateView","${detailProduct.toString()}")

        })
        viewModel.isDataLoading().observe(this, Observer {
            it.let {

                detail_progressBar.visible(it)

            }
        })


        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("detailFragOnViewCreated","${detailProduct.toString()}")
         detailImage = view.findViewById(R.id.detail_image_view)
        var activity = activity as AppCompatActivity
        activity.setSupportActionBar(detail_frag_toolbar as Toolbar)
        activity.supportActionBar?.setElevation(15.0F)
        activity.supportActionBar?.show()



    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        inflater?.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

}
