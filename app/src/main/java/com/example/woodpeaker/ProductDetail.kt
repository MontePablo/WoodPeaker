package com.example.woodpeaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.woodpeaker.adapters.SliderAdapter
import com.example.woodpeaker.databinding.ActivityProductDetailBinding
import com.example.woodpeaker.databinding.CustomViewAddonBinding
import com.example.woodpeaker.databinding.CustomViewRatingBinding
import com.example.woodpeaker.models.Product
import com.google.gson.Gson
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import java.util.*
import kotlin.collections.ArrayList

object ProductDetail : AppCompatActivity() {
    lateinit var binding:ActivityProductDetailBinding

    lateinit var product:Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product =Gson().fromJson(intent.getStringExtra("product"), Product::class.java)
        var slideAdapter=SliderAdapter()
        binding.sliderView.setSliderAdapter(slideAdapter)
        binding.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        binding.sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        binding.sliderView.startAutoCycle()

        if(product.images.redLink.isNotEmpty())
            slideAdapter.updateData(product.images.redLink)
        else if(product.images.whiteLink.isNotEmpty())
            slideAdapter.updateData(product.images.whiteLink)
        else if(product.images.blackLink.isNotEmpty())
            slideAdapter.updateData(product.images.blackLink)
        else if(product.images.blueLink.isNotEmpty())
            slideAdapter.updateData(product.images.blueLink)
        else if(product.images.greenLink.isNotEmpty())
            slideAdapter.updateData(product.images.greenLink)
        else if(product.images.yellowLink.isNotEmpty())
            slideAdapter.updateData(product.images.yellowLink)

        binding.colBlack.setOnClickListener(View.OnClickListener { slideAdapter.updateData(product.images.blackLink) })
        binding.colBlue.setOnClickListener(View.OnClickListener { slideAdapter.updateData(product.images.blueLink) })
        binding.colGreen.setOnClickListener(View.OnClickListener { slideAdapter.updateData(product.images.greenLink) })
        binding.colWhite.setOnClickListener(View.OnClickListener { slideAdapter.updateData(product.images.whiteLink) })
        binding.colRed.setOnClickListener(View.OnClickListener { slideAdapter.updateData(product.images.redLink) })
        binding.colYellow.setOnClickListener(View.OnClickListener { slideAdapter.updateData(product.images.yellowLink) })

        binding.productName.text=product.title
        binding.buynow.setOnClickListener(View.OnClickListener {
            OrderClickDialog.process(this,this,product,layoutInflater)
        })
        binding.tryon.setOnClickListener(View.OnClickListener {

        })
        binding.otherDetail.text=product.description

        addFeatures()
        addReviews()
        addAddons()


    }
    fun addReviews(){
        for(rating in product!!.ratings){
            val viewBinding=CustomViewRatingBinding.inflate(layoutInflater)
            viewBinding.name.text=rating.name
            viewBinding.comment.text=rating.comment
            viewBinding.date.text=rating.date
            viewBinding.ratingBar.rating=rating.rating.toFloat()
            binding.reviewsLayout.addView(viewBinding.root)
        }
    }
    fun addFeatures(){
        var s=String()
        for(f in product!!.features){
            s+=f+"\n"
        }
        binding.features.text=s
    }
    fun addAddons(){
        val viewBinding=CustomViewAddonBinding.inflate(layoutInflater)

        for(f in product.addons){

            Glide.with(this).load(f.imageLink).into(viewBinding.addonImage)
            viewBinding.addonPrice.text=f.price
            viewBinding.adddonName.text=f.name
            viewBinding.addonAdd.setOnClickListener(View.OnClickListener {
                f.quantity=(f.quantity.toInt()+1).toString()
                viewBinding.addonQuantity.text=f.quantity
            })
            viewBinding.addonRemove.setOnClickListener(View.OnClickListener {
                if(f.quantity.toInt()>=1){
                    f.quantity=(f.quantity.toInt()-1).toString()
                    viewBinding.addonQuantity.text=f.quantity
                }
            })
            binding.addonLayout.addView(viewBinding.root)
        }
    }
}