package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.woodpeaker.adapters.SliderAdapter
import com.example.woodpeaker.daos.FirebaseDao
import com.example.woodpeaker.databinding.ActivityProductDetailBinding
import com.example.woodpeaker.databinding.CustomViewAddonBinding
import com.example.woodpeaker.databinding.CustomViewRatingBinding
import com.example.woodpeaker.models.Order
import com.example.woodpeaker.models.Product
import com.google.gson.Gson
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations

class ProductDetail : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailBinding

    lateinit var product: Product
    lateinit var order: Order
    lateinit var productId:String
    var currentCol=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.lv345)
        order= Order()
        product = Gson().fromJson(intent.getStringExtra("product"), Product::class.java)
        productId=intent.getStringExtra("productId")!!
        var slideAdapter= SliderAdapter()
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

        if(product.images.redLink.isNotEmpty())
            binding.colRed.visibility=View.VISIBLE
        if(product.images.blueLink.isNotEmpty())
            binding.colBlue.visibility=View.VISIBLE
        if(product.images.greenLink.isNotEmpty())
            binding.colGreen.visibility=View.VISIBLE
        if(product.images.blackLink.isNotEmpty())
            binding.colBlack.visibility=View.VISIBLE
        if(product.images.yellowLink.isNotEmpty())
            binding.colYellow.visibility=View.VISIBLE
        if(product.images.whiteLink.isNotEmpty())
            binding.colWhite.visibility=View.VISIBLE

        binding.colBlack.setOnClickListener(View.OnClickListener { slideAdapter.updateData(product.images.blackLink)
        currentCol="black"})
        binding.colBlue.setOnClickListener(View.OnClickListener { slideAdapter.updateData(product.images.blueLink)
        currentCol="blue"})
        binding.colGreen.setOnClickListener(View.OnClickListener { slideAdapter.updateData(product.images.greenLink)
        currentCol="green"})
        binding.colWhite.setOnClickListener(View.OnClickListener { slideAdapter.updateData(product.images.whiteLink)
        currentCol="white"})
        binding.colRed.setOnClickListener(View.OnClickListener { slideAdapter.updateData(product.images.redLink)
        currentCol="red"})
        binding.colYellow.setOnClickListener(View.OnClickListener { slideAdapter.updateData(product.images.yellowLink)
        currentCol="yellow"})

        binding.productName.text=product.title
        binding.buynow.setOnClickListener(View.OnClickListener {
            orderCreate()
            OrderClickDialog.process(this,this, order,layoutInflater)
        })
        binding.otherDetail.text=product.description

        binding.tryon.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,ThreeDimentionalModelActivity::class.java))
        })

        addFeatures()
        addReviews()
        addAddons()

    }

    fun addReviews(){
        for(rating in product!!.ratings){
            val viewBinding= CustomViewRatingBinding.inflate(layoutInflater)
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
        for(f in product.addons){
            val viewBinding= CustomViewAddonBinding.inflate(layoutInflater)
            Glide.with(viewBinding.addonImage).load(f.imageLink).into(viewBinding.addonImage)
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
    fun orderCreate(){
        for(f in product.addons){
            if(f.quantity.toInt()>0){
                order.addons.add(f)
            }
        }
        order.shape=product!!.shape
        order.productId= productId
        order.clientId= FirebaseDao.auth.uid!!
        if (product.images.whiteLink.isNotEmpty()){
            order.image=product.images.whiteLink[0]
        }else if(product.images.redLink.isNotEmpty()){
            order.image=product.images.redLink.get(0)
        }else if(product.images.blueLink.isNotEmpty()){
            order.image=product.images.blueLink.get(0)
        }else if(product.images.blackLink.isNotEmpty()){
            order.image=product.images.blackLink.get(0)
        }else if(product.images.yellowLink.isNotEmpty()){
            order.image=product.images.yellowLink.get(0)
        }else if(product.images.whiteLink.isNotEmpty()){
            order.image=product.images.whiteLink.get(0)
        }
        order.title=product.title
        order.price=product.price
        order.color=currentCol
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}