package com.pickledpepper.storenolibs.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.pickledpepper.storenolibs.*
import com.pickledpepper.storenolibs.common.Categories
import com.pickledpepper.storenolibs.data.Product
import com.pickledpepper.storenolibs.ui.viewmodels.HomeViewModel
import com.pickledpepper.storenolibs.util.observeOnce
import kotlin.random.Random


class HomeFragment : Fragment() {
    val mainActivity: MainActivity
        get() = activity as MainActivity

    companion object {
        fun newInstance() = HomeFragment()
    }

    var categoryOfImageClickListner:View.OnClickListener? =null
    private var viewModel: HomeViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = HomeViewModel.getInstance(activity!!)

        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backpackImage = view?.findViewById(R.id.image_backpack) as ImageView
        val poloImage = view?.findViewById(R.id.polo_image) as ImageView
        val pantsImage = view?.findViewById(R.id.pants_image) as ImageView
        val socksImage = view?.findViewById(R.id.socks_image) as ImageView
        val sweatshirtImage = view?.findViewById(R.id.sweatshirt_image) as ImageView

        categoryOfImageClickListner = View.OnClickListener { view ->

            when (view.id) {

                R.id.image_backpack -> {// viewModel?.setCategory(Categories.BACKPACKS.storeCategories)
                    var args = Bundle()
                    args.putString("CATEGORY", Categories.BACKPACKS.storeCategories)
                    val productFrag = ProductListFragment()

                    productFrag.arguments = args
                    val fragmentManger = activity?.let {

                        if (it is FragmentActivity)
                            it.supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, productFrag, "PRODUCTFRAG")
                                //    .addToBackStack(null)
                                .commit()
                        Log.d("HOMEFRAMENT", "Navigation")
                    }
                }
                R.id.polo_image -> {
                    //viewModel?.setCategory(Categories.POLOS.storeCategories)
                    var args = Bundle()
                    args.putString("CATEGORY", Categories.POLOS.storeCategories)
                    val productFrag = ProductListFragment()

                    productFrag.arguments = args
                    val fragmentManger = activity?.let {

                        if (it is FragmentActivity)
                            it.supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, productFrag, "PRODUCTFRAG")
                                //    .addToBackStack(null)
                                .commit()
                        Log.d("HOMEFRAMENT", "Navigation")

                    }
                }
                R.id.pants_image -> {
                    //   viewModel?.setCategory(Categories.PANTS.storeCategories)
                    var args = Bundle()
                    args.putString("CATEGORY", Categories.PANTS.storeCategories)
                    val productFrag = ProductListFragment()

                    productFrag.arguments = args
                    val fragmentManger = activity?.let {

                        if (it is FragmentActivity)
                            it.supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, productFrag, "PRODUCTFRAG")
                                //    .addToBackStack(null)
                                .commit()
                        Log.d("HOMEFRAMENT", "Navigation")
                    }
                }
                R.id.socks_image -> {//viewModel?.setCategory(Categories.SOCKS.storeCategories)
                    var args = Bundle()
                    args.putString("CATEGORY", Categories.SOCKS.storeCategories)
                    val productFrag = ProductListFragment()

                    productFrag.arguments = args
                    val fragmentManger = activity?.let {

                        if (it is FragmentActivity)
                            it.supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, productFrag, "PRODUCTFRAG")
                                //    .addToBackStack(null)
                                .commit()
                        Log.d("HOMEFRAMENT", "Navigation")
                    }
                }
                R.id.sweatshirt_image -> {//viewModel?.setCategory(Categories.SWEATSHIRTS.storeCategories)

                    var args = Bundle()
                    args.putString("CATEGORY", Categories.SWEATSHIRTS.storeCategories)
                    val productFrag = ProductListFragment()

                    productFrag.arguments = args
                    val fragmentManger = activity?.let {

                        if (it is FragmentActivity)
                            it.supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, productFrag, "PRODUCTFRAG")
                                //    .addToBackStack(null)
                                .commit()
                        Log.d("HOMEFRAMENT", "Navigation")

                    }
                }
            }
        }
            backpackImage.setOnClickListener(categoryOfImageClickListner)
            socksImage.setOnClickListener(categoryOfImageClickListner)
            pantsImage.setOnClickListener(categoryOfImageClickListner)
            poloImage.setOnClickListener(categoryOfImageClickListner)
            sweatshirtImage.setOnClickListener(categoryOfImageClickListner)

        }



    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)


      /*  var categoryObservable :LiveData<String> = viewModel?.getCategoryForNavigation()!!

            categoryObservable.observeOnce(viewLifecycleOwner, Observer {

                categoryObservable.removeObservers(this)
                it.let {
                    var args = Bundle()
                    args.putString("CATEGORY", it)
                    val productFrag = ProductListFragment()

                    productFrag.arguments = args


                    val fragmentManger = activity?.let {

                        if (it is FragmentActivity)
                            it.supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, productFrag, "PRODUCTFRAG")
                            //    .addToBackStack(null)
                                .commit()
                        Log.d("HOMEFRAMENT", "Navigation")

                        //viewModel?.ResetCategoryForNav()

                    }


                }


            })*/
        }





    }








