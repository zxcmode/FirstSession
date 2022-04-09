package com.monkeydluffyd.firstsession

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SignInScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_screen)
        supportActionBar?.hide()
    }
}