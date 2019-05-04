package com.pickledpepper.storenolibs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pickledpepper.storenolibs.data.Product
import com.pickledpepper.storenolibs.repository.Repository
import com.pickledpepper.storenolibs.ui.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.product_list_fragment.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener   {


    lateinit var drawer: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = FirebaseAuth.getInstance()!!
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        if (auth.currentUser == null && savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                SignUpFirebaseFragment()
            )
                .addToBackStack(null)
                .commit()
        }else{
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment() ).commit()
        }
        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        // get toolbar fromFrament  pass to actionbarDrawerTogle()



    }

    override fun onBackPressed() {




        var myfragment =supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        }
        else if(myfragment is ProductListFragment ){

            this.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment() ).commit()
            Log.d("ONBACKPRESSEDINCOND", "${supportFragmentManager.fragments}")
        }else {
            Log.d("ONBACKPRESSED", "${supportFragmentManager.fragments}")
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId){
            R.id.nav_home ->{
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment() ).commit()
            }
            R.id.nav_favorites ->{
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FavoritesFragment() ).addToBackStack(null).commit()
            }
            R.id.nav_shopping_kart ->{
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ShoppingKartFragment() ).addToBackStack(null).commit()
            }
            R.id.nav_profile -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ProfileFragment()).addToBackStack(null).commit()
            }



        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

     fun openDrawer() {

        drawer.openDrawer(GravityCompat.START);
    }

    }

