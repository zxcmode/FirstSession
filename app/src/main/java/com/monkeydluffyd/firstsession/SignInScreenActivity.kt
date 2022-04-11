package com.monkeydluffyd.firstsession

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.monkeydluffyd.firstsession.databinding.ActivitySignInScreenBinding


class SignInScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInScreenBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance() // firebase
        supportActionBar?.hide() // hide bar


        binding.btnSignIn.setOnClickListener {
            SignIn()

        }

        binding.btnSignUp.setOnClickListener {
            SignUp()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
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
}