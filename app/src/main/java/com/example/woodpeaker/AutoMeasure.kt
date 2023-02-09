package com.example.woodpeaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.woodpeaker.models.Product
import com.google.gson.Gson

class AutoMeasure : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_measure)

        var product = Gson().fromJson(intent.getStringExtra("product"), Product::class.java)
    }
}