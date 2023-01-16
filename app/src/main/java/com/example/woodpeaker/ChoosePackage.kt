package com.example.woodpeaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.woodpeaker.databinding.ActivityChoosePackageBinding

class ChoosePackage : AppCompatActivity() {
    lateinit var  binding: ActivityChoosePackageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChoosePackageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}