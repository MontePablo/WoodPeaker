package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.woodpeaker.databinding.ActivityBulkUserPackagesBinding

class BulkUserPackages : AppCompatActivity() {

    lateinit var binding: ActivityBulkUserPackagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBulkUserPackagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.package1.setOnClickListener(View.OnClickListener { startActivity(Intent(applicationContext, Login::class.java))
        })
        binding.package2.setOnClickListener(View.OnClickListener { startActivity(Intent(applicationContext, Login::class.java))
        })
    }
}