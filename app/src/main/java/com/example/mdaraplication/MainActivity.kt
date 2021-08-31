package com.example.mdaraplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mdaraplication.login.MDARLoginActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        var actionBar = supportActionBar
        supportActionBar?.hide()
        setContentView(R.layout.logo);
        Thread.sleep(2000)
        startActivity(Intent(this, MDARLoginActivity :: class.java));
    }
}