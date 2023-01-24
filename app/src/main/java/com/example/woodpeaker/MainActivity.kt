package com.example.woodpeaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.woodpeaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.shadowlight)



    }
}