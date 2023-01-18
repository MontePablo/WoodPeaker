package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.woodpeaker.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signIn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java)) })
        binding.signUp.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java)) })
    }
}