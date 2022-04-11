package com.monkeydluffyd.firstsession

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.monkeydluffyd.firstsession.databinding.ActivitySignInScreenBinding


class SignInScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInScreenBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance() // firebase
        supportActionBar?.hide() // hide bar

        Buttons()

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

    }

    private fun SignIn()
    {
        auth.signInWithEmailAndPassword(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
            .addOnCompleteListener(this) {
               task -> if (task.isSuccessful)
            {
                   Toast.makeText(baseContext, "Авторизован", Toast.LENGTH_LONG).show()
            }
                else
            {
                Toast.makeText(baseContext, "Ошибка", Toast.LENGTH_LONG).show()
            }
            }
    }

    private fun SignUp()
    {
        auth.createUserWithEmailAndPassword(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
            .addOnCompleteListener(this) {
                    task -> if (task.isSuccessful)
            {
                Toast.makeText(baseContext, "Зарегистрирован", Toast.LENGTH_LONG).show()
            }
            else
            {
                Toast.makeText(baseContext, "Ошибка", Toast.LENGTH_LONG).show()
            }
            }
    }

    private fun SignInWithGoogle()
    {
        var signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    @Override
    private fun Buttons()
        {
        binding.btnSignIn.setOnClickListener {
            SignIn()
        }

        binding.btnSignUp.setOnClickListener {
            SignUp()
        }

        binding.btnGoogle.setOnClickListener {
            SignInWithGoogle()
        }
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}