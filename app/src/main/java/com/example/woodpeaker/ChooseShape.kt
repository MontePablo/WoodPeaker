package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.woodpeaker.databinding.ActivityChooseShapeBinding
import com.example.woodpeaker.models.Product

class ChooseShape : AppCompatActivity() {
    lateinit var binding:ActivityChooseShapeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChooseShapeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ikitchen.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, Products::class.java)
            intent.putExtra("shape", "I shape kitchen")
            startActivity(intent)
        })
        binding.ukitchen.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, Products::class.java)
            intent.putExtra("shape", "U shape kitchen")
            startActivity(intent)
        })
        binding.lkitchen.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, Products::class.java)
            intent.putExtra("shape", "L shape kitchen")
            startActivity(intent)
        })
        binding.islandkitchen.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, Products::class.java)
            intent.putExtra("shape", "Island shape kitchen")
            startActivity(intent)
        })

    }
}