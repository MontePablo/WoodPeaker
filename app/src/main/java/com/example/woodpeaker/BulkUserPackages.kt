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
        binding.package1.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Login::class.java)
            intent.putExtra("pack","3000")
            startActivity(intent)
        })
        binding.package2.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Login::class.java)
            intent.putExtra("pack","12000")
            startActivity(intent)
        })
    }
}