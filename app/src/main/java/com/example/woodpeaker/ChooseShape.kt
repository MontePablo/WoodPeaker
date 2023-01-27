package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.woodpeaker.databinding.ActivityChooseShapeBinding

class ChooseShape : AppCompatActivity() {
    lateinit var binding:ActivityChooseShapeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChooseShapeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ikitchen.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, ProductDetail::class.java))
        })
        binding.ukitchen.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, ProductDetail::class.java))
        })
    }
}