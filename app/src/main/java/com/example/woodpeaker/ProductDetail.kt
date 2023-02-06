package com.example.woodpeaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.woodpeaker.databinding.ActivityProductDetailBinding
import com.example.woodpeaker.models.Product
import com.google.gson.Gson
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations

class ProductDetail : AppCompatActivity() {
    lateinit var binding:ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = Gson().fromJson(intent.getStringExtra("product"), Product::class.java)
        loadData(product)
    }

    fun loadData(product: Product) {
        binding.sliderView.setSliderAdapter(SliderAdapter(prod!!.images))
        binding.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        binding.sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        binding.sliderView.startAutoCycle()

        var bluePics=ArrayList<String>()
        var redPics=ArrayList<String>()
        var blackPics=ArrayList<String>()
        var yellowPics=ArrayList<String>()
        var greenPics=ArrayList<String>()
        var whitePics=ArrayList<String>()
        if(product)
    }

}