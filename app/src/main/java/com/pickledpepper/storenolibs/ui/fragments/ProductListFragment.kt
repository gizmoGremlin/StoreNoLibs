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
import com.pickledpepper.storenolibs.util.reObserve


class ProductListFragment : Fragment() {

    companion object {
        fun newInstance() = ProductListFragment()
    }
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

        }else{
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
                Log.d("PRODUCTLISTSCROLLED", isLastVisible().toString())
                if (isLastVisible()) {
                    viewModel.loadProducts(theBundle)

                    Log.d("PRODUCTLISTSCROLLED", dx.toString())
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
        activity.supportActionBar?.show()



        viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
        // TODO: Use the ViewModel
        populateDataBase()
        viewModel.loadProducts(theBundle)
        viewModel.getProducts().reObserve(viewLifecycleOwner, Observer {
            it.let {
                Log.d("Fragment", it.toString())
                showProductList(it)
            }
        })

    }

    private fun populateDataBase() {


        var productList=createListOfFakeProducts()
        viewModel.persistProductList(productList,"productList" )
        viewModel.isDataLoading().observe(viewLifecycleOwner, Observer {
            it.let {
                if (!swipe_refresh_product_list.isRefreshing){
                    progressBar.visible(it)
                }
            }
        })

    }
    private fun createListOfFakeProducts() : List<Product> {
            val listOfCategories = listOf<String>(Categories.SOCKS.storeCategories, Categories.SWEATSHIRTS.storeCategories,
                Categories.POLOS.storeCategories, Categories.PANTS.storeCategories,Categories.BAGS.storeCategories,Categories.BACKPACKS.storeCategories)
        val listOfRandomWords =listOf<String>("Fuzzy", "Warm", "soft", "comfy", "the", "a","white", "red","home","formal","our","happy","black",
            "scent free", "soft","indoor", "rubber", "wool","girl", "boy","formal")

        var fakeProductList :MutableList<Product> = mutableListOf<Product>()

        var listOfPicturesOfSocks = mutableListOf<String>(
            "https://cdn11.bigcommerce.com/s-j602wc6a/images/stencil/2048x2048/products/6526/23532/bluebell-crew-socks-recycled-cotton-w__92655.1502809282.jpg?c=2",
            "https://cdn.shopify.com/s/files/1/1586/5305/products/PDX_Orig_1001-2_1024x1024.jpg?v=1550011196","https://cdn.shopify.com/s/files/1/1212/2956/products/Bundleeaster2_2048x.jpg?v=1527312186")
        var listOfPicturesOfBackpacks = mutableListOf<String>(
            "https://shop.samsonite.com/dw/image/v2/AAUE_PRD/on/demandware.static/-/Sites-product-catalog/default/dwcef958a9/collections/_samsonite/valt/500x500/1207991041-slimbp-back.jpg?sw=1500",
                    "https://cdn.gearpatrol.com/wp-content/uploads/2018/08/The-25-Best-Backpacks-for-Everyday-Use-gear-patrol-full-lead.jpg",
            "https://www.gregorypacks.com/dw/image/v2/AAUE_PRD/on/demandware.static/-/Sites-product-catalog/default/dw337f3b46/collections/_gregory/Zulu/500x500/GMP-S19-Zulu40_MantisGreen_Front34.jpg?sw=1500&sfrm=png&q=70",
            "https://s7d2.scene7.com/is/image/academy/20254167")
        var listOfPicsBags = mutableListOf<String>("https://images-na.ssl-images-amazon.com/images/I/81oX14KbNJL._UY695_.jpg",
            "https://images-na.ssl-images-amazon.com/images/I/71H%2BJxJCCGL._UX679_.jpg","https://images-na.ssl-images-amazon.com/images/I/71TepuOQCoL._UX695_.jpg",
            "https://images-na.ssl-images-amazon.com/images/I/618HQXDkswL._UY695_.jpg","https://images-na.ssl-images-amazon.com/images/I/7120bJ6082L._UX679_.jpg",
            "https://images-na.ssl-images-amazon.com/images/I/41yf4ow3FtL.jpg"
            )
        var listOfPicsPolos = mutableListOf<String>("https://factory.jcrew.com/s7-img-facade/J0351_YL5230_m?fmt=jpeg&qlt=90,0&resMode=sharp&op_usm=.1,0,0,0&wid=408&hei=408",
           "https://www.loding.ca/2938-big_default/pure-mercerized-cotton-polo-shirt.jpg","https://elbeco.com/wp-content/uploads/2015/05/K5100-756x900.jpg",
            "https://tristanpush-120f.kxcdn.com/image_produit/395x593/HV020D1062Z_OR50_1.jpg", "https://www.loding.ca/2936-home_default/pure-mercerized-cotton-polo-shirt.jpg")
        var listOfPicsSweatShirts =  mutableListOf<String>("https://cdn.shopify.com/s/files/1/2404/6643/products/FA16-WFJUN-BLK_29670b2d-b2f4-43f4-a530-757e6f7dafbd_703x893.progressive.jpg?v=1541196332",
            "https://www.dhresource.com/webp/m/0x0s/f2-albu-g5-M00-F1-2D-rBVaJFmws8eALOWOAALGMDnsHxI944.jpg/wholesale-echoine-short-sweatshirts-women.jpg",
            "https://cdn.shopify.com/s/files/1/0835/3729/products/ivory-ella-women-s-sweatshirts-xs-moonstone-organic-french-terry-crewneck-6311641481331_1200x.jpg?v=1551465460",
            "https://www.forever21.com/images/default_330/00342906-02.jpg",
            "https://cdn.shopify.com/s/files/1/0835/3729/products/ivory-ella-women-s-sweatshirts-xs-rose-quartz-organic-cropped-hoodie-6311661338739_1050x.jpg?v=1551465702",
            "https://www.decathlon.co.uk/media/837/8371659/big_987208.jpg","https://www.decathlon.co.uk/media/839/8395905/big_1349953.jpg")
        var listOfPicsPants = mutableListOf<String>("https://www.dhresource.com/webp/m/0x0s/f2-albu-g6-M00-11-E1-rBVaSFqwEOOAHPIdAAIKoPxOiZU535.jpg/women-039;s-fitness-leggings-workout-pants-high-waist-leggings-ladies-sports-yoga-pants-fitness-running-long-trousers-legging.jpg",
            "https://images-na.ssl-images-amazon.com/images/I/51thJsi9p0L._UX679_.jpg","https://fabletics-us-cdn.justfab.com/media/images/products/PT1934317-0001/PT1934317-0001-2_327x491.jpg",
            "https://fabletics-us-cdn.justfab.com/media/images/products/PT1931791-0001/PT1931791-0001-2_327x491.jpg",
            "https://fabletics-us-cdn.justfab.com/media/images/products/PT1828147-4899/PT1828147-4899-2_327x491.jpg")


        for (i in 1..4){
            var randCategory =listOfCategories.get(Random.nextInt(listOfCategories.size))

          when (randCategory){
              Categories.SOCKS.storeCategories -> fakeProductList.add(Product(
                  Random.nextInt(6000000),randCategory, listOfRandomWords.get((1..20).shuffled().last()) ,
                  "${listOfRandomWords.get(Random.nextInt(20))} is a  ${listOfRandomWords.get(Random.nextInt(20))} and " +
                          "${listOfRandomWords.get(Random.nextInt(20))}", Random.nextDouble(100.00),
                  listOfPicturesOfSocks.get(Random.nextInt(listOfPicturesOfSocks.size-1))))
              Categories.BACKPACKS.storeCategories -> fakeProductList.add(Product(
                  Random.nextInt(6000000),randCategory,listOfRandomWords.get((1..20).shuffled().last()),
                  "${listOfRandomWords.get(Random.nextInt(20))} is a  ${listOfRandomWords.get(Random.nextInt(20))} and " +
                          "${listOfRandomWords.get(Random.nextInt(20))}", Random.nextDouble(100.00),
                  listOfPicturesOfBackpacks.get(Random.nextInt(listOfPicturesOfBackpacks.size-1))))
              Categories.BAGS.storeCategories -> fakeProductList.add(Product(
                  Random.nextInt(6000000),randCategory,listOfRandomWords.get((1..20).shuffled().last()),
                  "${listOfRandomWords.get(Random.nextInt(20))} is a  ${listOfRandomWords.get(Random.nextInt(20))} and " +
                          "${listOfRandomWords.get(Random.nextInt(20))}", Random.nextDouble(100.00),
                  listOfPicsBags.get(Random.nextInt(listOfPicsBags.size-1))))
              Categories.PANTS.storeCategories ->  fakeProductList.add(Product(
                  Random.nextInt(6000000),randCategory,listOfRandomWords.get((1..20).shuffled().last()),
                  "${listOfRandomWords.get(Random.nextInt(20))} is a  ${listOfRandomWords.get(Random.nextInt(20))} and " +
                          "${listOfRandomWords.get(Random.nextInt(20))}", Random.nextDouble(100.00),
                  listOfPicsPants.get(Random.nextInt(listOfPicsPants.size-1))))
              Categories.POLOS.storeCategories -> fakeProductList.add(Product(
                  Random.nextInt(6000000),randCategory,listOfRandomWords.get((1..20).shuffled().last()),
                  "${listOfRandomWords.get(Random.nextInt(20))} is a  ${listOfRandomWords.get(Random.nextInt(20))} and " +
                          "${listOfRandomWords.get(Random.nextInt(20))}", Random.nextDouble(100.00),
                  listOfPicsPolos.get(Random.nextInt(listOfPicsPolos.size-1))))
              Categories.SWEATSHIRTS.storeCategories ->  fakeProductList.add(Product(
                  Random.nextInt(6000000),randCategory,listOfRandomWords.get((1..20).shuffled().last()),
                  "${listOfRandomWords.get(Random.nextInt(20))} is a  ${listOfRandomWords.get(Random.nextInt(20))} and " +
                          "${listOfRandomWords.get(Random.nextInt(20))}", Random.nextDouble(100.00),
                  listOfPicsSweatShirts.get(Random.nextInt(listOfPicsSweatShirts.size-1))))

          }




            Log.d("Inside create Fake", fakeProductList.get(fakeProductList.size-1).toString())



        }

        return fakeProductList

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {


        inflater?.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         when(item?.itemId){
            R.id.shoppingkart_menu -> {
                //transaction

                Log.d("InOptionsMenu", "ShoppingKart clicked")
                return true
            }
            R.id.favorites_menu -> {
                 activity.let {
                    if (it is FragmentActivity)
                        it.supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container,FavoritesFragment(),"Favorites_FRAGMENT")
                            .addToBackStack(null)
                            .commit()
                    Log.d("InOptionsMenu", "Favs clicked")
                }
                Log.d("InOptionsMenu", "Favs clicked")
                return true
            }


            }




        return super.onOptionsItemSelected(item)
    }

    fun isLastVisible(): Boolean {
        val layoutManager = product_list_recycler_view.getLayoutManager() as GridLayoutManager
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        val numItems = product_list_recycler_view.getAdapter()?.getItemCount()
        Log.d("isLastVisibleNum","${numItems.toString()}")
        Log.d("isLastVisiblePos","${pos.toString()}")
        if (numItems != null)return pos >= numItems-1 else return false

    }


}

