package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.woodpeaker.adapters.productFuntions
import com.example.woodpeaker.databinding.ActivityProductsSurfBinding
import com.example.woodpeaker.models.Product
import com.google.gson.Gson

class ProductsSurf : AppCompatActivity(),productFuntions {
    lateinit var binding:ActivityProductsSurfBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityProductsSurfBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




    }


    override fun productClick(product: Product) {
        val gson = Gson()
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("product", gson.toJson(product))
        startActivity(intent)
    }
}