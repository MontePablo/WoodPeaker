package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.woodpeaker.databinding.ActivitySelectYourPackageBinding

class SelectYourPackage : AppCompatActivity() {
    lateinit var binding: ActivitySelectYourPackageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySelectYourPackageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNext.setOnClickListener(View.OnClickListener{startActivity(Intent(applicationContext, BulkUserPackages::class.java))
        })
    }
}