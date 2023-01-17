package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
//        actionBar?.hide()
        Handler().postDelayed({
            startActivity(Intent(applicationContext, Intro::class.java))
            finish()
        }, 3100)
    }
}