package com.cis102y.wellbeing.ui.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cis102y.wellbeing.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.btnLogin)
            startSignIn()
    }

    private fun startSignIn() {

    }
}