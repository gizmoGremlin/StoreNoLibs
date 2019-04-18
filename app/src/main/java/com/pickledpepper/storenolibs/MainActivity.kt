package com.pickledpepper.storenolibs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pickledpepper.storenolibs.data.Product
import com.pickledpepper.storenolibs.repository.Repository
import com.pickledpepper.storenolibs.ui.fragments.AuthFragment
import com.pickledpepper.storenolibs.ui.fragments.DetailFragment
import com.pickledpepper.storenolibs.ui.fragments.HomeFragment
import com.pickledpepper.storenolibs.ui.fragments.ProductListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.product_list_fragment.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity()   {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val auth = FirebaseAuth.getInstance()!!

        if (auth.currentUser == null && savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                AuthFragment()
            )
                .addToBackStack(null)
                .commit()
        }else{
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment() ).commit()
        }
    }

    override fun onBackPressed() {

        var myfragment =supportFragmentManager.findFragmentById(R.id.fragment_container)

        if(myfragment is ProductListFragment ){

            this.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment() ).commit()
            Log.d("ONBACKPRESSEDINCOND", "${supportFragmentManager.fragments}")
        }else {
            Log.d("ONBACKPRESSED", "${supportFragmentManager.fragments}")
            super.onBackPressed()
        }
    }



    }

