package com.pickledpepper.storenolibs.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth

import com.pickledpepper.storenolibs.R
import com.pickledpepper.storenolibs.ui.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }
    lateinit var auth: FirebaseAuth
    lateinit var email:EditText
    lateinit var password: EditText
    lateinit var signinBtn: Button

    private lateinit var viewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = view.findViewById(R.id.signin_email_edittext)
        password = view.findViewById(R.id.signin_password_edittext)
        signinBtn = view.findViewById(R.id.signin_button_login_frag)

        signinBtn.setOnClickListener({
            signinUser()
        })
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()


        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun signinUser() {

        var mEmail = email.getText().toString().trim()
        var mPassword = password.getText().toString().trim()


        if (!(mPassword.length > 7)) {
            Toast.makeText(context, "invalid Password", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(mEmail,mPassword)
            .addOnCompleteListener({
                if(it.isSuccessful){
                    navToHomeFragment()
                }
                else{
                    Toast.makeText(context, "Error in email or password", Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun navToHomeFragment() {
        val homeFrag = HomeFragment()
        val fragmentManger = activity?.let {
            if (it is FragmentActivity)
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, homeFrag, "HOMEFRAG")
                    // .addToBackStack(null)       // how to work with the backstack???
                    .commit()
        }
    }

}
