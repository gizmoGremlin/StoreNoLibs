package com.pickledpepper.storenolibs.ui.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.OnCompleteListener
import com.pickledpepper.storenolibs.R
import com.pickledpepper.storenolibs.data.UserProfile
import com.pickledpepper.storenolibs.ui.viewmodels.SignInViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignUpFirebaseFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var fullName: EditText
    lateinit var username: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var registerBtn: Button
    lateinit var viewModel: SignInViewModel
    lateinit var signinBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
        fullName = view.findViewById(R.id.signup_full_name)
        username = view.findViewById(R.id.signup_username)
        email = view.findViewById(R.id.signup_email)
        password = view.findViewById(R.id.signup_password)
        registerBtn = view.findViewById(R.id.btn_register)
        signinBtn = view.findViewById(R.id.sign_in_button)

        signinBtn.setOnClickListener({
            val fragmentManger = activity?.let {
                if (it is FragmentActivity)
                    it.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, LoginFragment(), "HOMEFRAG")
                        // .addToBackStack(null)       // how to work with the backstack???
                        .commit()
            }
        })
        registerBtn.setOnClickListener(View.OnClickListener {
            registerUser()
        })


    }

    private fun registerUser() {
        var mName = fullName.getText().toString().trim()
        var mEmail = email.getText().toString().trim()
        var mPassword = password.getText().toString().trim()
        var mUsername = username.getText().toString().trim()


        if (!(mName.length > 6)) {

            Toast.makeText(context, "invalid Name", Toast.LENGTH_SHORT).show()
            return
        }
        if (!isValidEmail(email.text)) {

            Toast.makeText(context, "invalid Email", Toast.LENGTH_SHORT).show()
            return
        }
        if (!(mUsername.length > 6)) {

            Toast.makeText(context, "valid Username", Toast.LENGTH_SHORT).show()
            return
        }
        if (!(mPassword.length > 7)) {
            Toast.makeText(context, "invalid Password", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(mEmail, mPassword)
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful) {

                    Toast.makeText(context, "User created", Toast.LENGTH_SHORT).show()
                    // create new profile and persist
                    viewModel.persistUser(UserProfile(auth.uid!!, mName, mUsername, mPassword, mEmail))

                    exchangeHomeFragment()
                } else {
                    Toast.makeText(context, "Error creating user", Toast.LENGTH_SHORT).show()
                }

            })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()


        if (auth.currentUser != null) {
            exchangeHomeFragment()
        }




        return inflater.inflate(R.layout.fragment_sign_up_firebase, container, false)
    }

    private fun exchangeHomeFragment() {
        val homeFrag = HomeFragment()
        val fragmentManger = activity?.let {
            if (it is FragmentActivity)
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, homeFrag, "HOMEFRAG")
                    // .addToBackStack(null)       // how to work with the backstack???
                    .commit()
        }
    }

    fun isValidEmail(target: CharSequence): Boolean {

        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}



