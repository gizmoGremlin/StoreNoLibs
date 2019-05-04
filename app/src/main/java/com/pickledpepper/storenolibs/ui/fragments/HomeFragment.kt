package com.pickledpepper.storenolibs.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pickledpepper.storenolibs.*
import com.pickledpepper.storenolibs.adapter.HomeListAdapter
import com.pickledpepper.storenolibs.common.Categories
import com.pickledpepper.storenolibs.data.Product
import com.pickledpepper.storenolibs.ui.viewmodels.HomeViewModel
import com.pickledpepper.storenolibs.util.SpacesItemDecoration
import com.pickledpepper.storenolibs.util.observeOnce
import kotlinx.android.synthetic.main.home_fragment.*
import kotlin.random.Random


class HomeFragment : Fragment() {
    val mainActivity: MainActivity
        get() = activity as MainActivity

    companion object {
        fun newInstance() = HomeFragment()
    }
    private lateinit var mAdapter : HomeListAdapter

    private var viewModel: HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = HomeViewModel.getInstance(activity!!)
        viewModel?.loadCategories()
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = this.context?.let { HomeListAdapter(it) }!!
        val myLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        home_frag_recyclerview.addItemDecoration(SpacesItemDecoration(4))

        home_frag_recyclerview.apply {
            adapter = mAdapter
            mAdapter.onItemClick = {
                var frag =  ProductListFragment()
                var myCategoryBundle  = Bundle()
                myCategoryBundle.putString("CATEGORY", it.categoryName )

                frag.arguments = myCategoryBundle
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(com.pickledpepper.storenolibs.R.id.fragment_container, frag)
                    .commit()
            }
            setHasFixedSize(true)
            layoutManager = myLayoutManager
            Log.d("HomeAdapter",layoutManager.toString())
            viewModel?.getCategoryList()?.observe(viewLifecycleOwner, Observer {
                    it.let {
                        Log.d("home getCategory",it.toString())
                        mAdapter.setAdapterList(it)
                    }
            })
        }


        }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        var activity = activity as AppCompatActivity
        activity.setSupportActionBar(home_frag_toolbar as Toolbar)
        activity.supportActionBar?.setElevation(15.0F)
       // activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        activity.supportActionBar?.show()
        super.onActivityCreated(savedInstanceState)



        }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Log.d("InHomeOptionsMenue", "hi")



        return super.onOptionsItemSelected(item)
    }


    }








