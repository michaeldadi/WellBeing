package com.cis102y.wellbeing.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cis102y.wellbeing.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth
private lateinit var googleSignInClient: GoogleSignInClient
private lateinit var callbackManager: CallbackManager

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //Switch to Sign In Activity
        findViewById<TextView>(R.id.gotoSignIn).setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))
        }
        //Create new user account with email/password
        val editTextEmail = findViewById<EditText>(R.id.inputEmail)
        val editTextPassword = findViewById<EditText>(R.id.inputPassword)
        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            auth.createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString())
                .addOnCompleteListener(this) {task1 ->
                    if (task1.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        //Update profile info
                        val profileUpdates = userProfileChangeRequest {
                            displayName = ""
                        }
                        user!!.updateProfile(profileUpdates).addOnCompleteListener { task2 ->
                            if (task2.isSuccessful)
                                Log.d(TAG, "User profile updated.")
                        }
                        //TODO: implement EditTexts in ui to get user inputs
                        val userInfo = hashMapOf(
                            "firstName" to "",
                            "lastName" to "",
                            "phoneNumber" to "",
                            "emailVerified" to false,
                            "gender" to "",
                            "age" to 0,
                            "registrationTimestamp" to FieldValue.serverTimestamp()
                        )
                        val db = FirebaseFirestore.getInstance()
                        db.collection("users").document(user.uid)
                            .set(userInfo)
                            .addOnCompleteListener { task3 ->
                                if (task3.isSuccessful)
                                    Log.d(TAG, "DocumentSnapshot successfully written!")
                                else
                                    Log.w(TAG, "Error writing document", task3.exception)
                            }
                        //Send account verification email
                        user.sendEmailVerification()
                        finish()
                        //updateUI(user)
                    }
                    else {
                        Log.w(TAG, "createUserWithEmail:failure", task1.exception)
                        Snackbar.make(it, "Authentication failed.", Snackbar.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        }
        //Define user data being requested from Google account (basic info, email, & ID token)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        //Sign in with Google account
        findViewById<ImageView>(R.id.googleLogin).setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
        }
        val facebookLogin = findViewById<LoginButton>(R.id.btnFacebookLogin)
        //Convert click on custom button to click on Facebook LoginButton
        findViewById<ImageView>(R.id.facebookLogin).setOnClickListener { facebookLogin.performClick() }
        //Sign in with Facebook account
        facebookLogin.setOnClickListener {
            callbackManager = CallbackManager.Factory.create()
            //Define user data being requested from Facebook account (email, public profile)
            facebookLogin.setPermissions("email", "public_profile")
            facebookLogin.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult?) {
                    Log.d(TAG, "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult!!.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook:onCancel")
                }

                override fun onError(error: FacebookException?) {
                    Log.d(TAG, "facebook:onError", error)
                }
            })
        }

        auth = Firebase.auth
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
                // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    try {
                        // Google Sign In was successful, authenticate with Firebase
                        val account = task.getResult(ApiException::class.java)!!
                        Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                        firebaseAuthWithGoogle(account.idToken!!)
                    } catch (e: ApiException) {
                        // Google Sign In failed, update UI appropriately
                        Log.w(TAG, "Google sign in failed", e)
                        //updateUI(null)
                    }
            callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        //showProgressBar()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    finish()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)

                    Snackbar.make(window.decorView.rootView, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")
        //showProgressBar()
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Snackbar.make(window.decorView.rootView, "Authentication failed.", Snackbar.LENGTH_SHORT).show()
                    //updateUI(null)
                }
                //hideProgressBar()
            }
    }
    companion object {
        private const val TAG = "SignUpActivity"
        private const val RC_SIGN_IN = 123
    }
}
