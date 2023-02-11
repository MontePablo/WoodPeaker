package com.example.woodpeaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.woodpeaker.models.Order
import com.example.woodpeaker.models.Product
import com.google.gson.Gson

class AutoMeasure : AppCompatActivity() {
    lateinit var order: Order
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_measure)
        order = Gson().fromJson(intent.getStringExtra("order"), Order::class.java)
    }
}