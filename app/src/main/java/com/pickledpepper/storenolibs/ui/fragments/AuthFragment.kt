package com.pickledpepper.storenolibs.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.pickledpepper.storenolibs.BuildConfig

import com.pickledpepper.storenolibs.R
import kotlinx.android.synthetic.main.fragment_auth.*


class AuthFragment : Fragment() {
    private val RC_SIGN_IN = 123
    val auth = FirebaseAuth.getInstance()!!

    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (auth.currentUser != null) {
            exchangeHomeFragment()

        } else {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){

            val response = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK){
                /*
                    Checks if the User sign in was successful
                 */
//                startActivity(Next Activity)


                exchangeHomeFragment()


                return
            }
            else {
                if(response == null){
                    //If no response from the Server

                    return
                }
                if(response.error?.errorCode == ErrorCodes.NO_NETWORK){
                    //If there was a network problem the user's phone

                    return
                }
                if(response.error?.errorCode == ErrorCodes.UNKNOWN_ERROR){
                    //If the error cause was unknown

                    return
                }
            }
        }

    }

    private fun exchangeHomeFragment() {
        val homeFrag = HomeFragment()
        val fragmentManger = activity?.let{
            if (it is FragmentActivity)
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,homeFrag,"PRODUCTFRAG")
                   // .addToBackStack(null)       // how to work with the backstack???
                    .commit()
        }    }
}

