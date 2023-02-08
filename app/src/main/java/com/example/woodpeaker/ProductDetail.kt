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
//    lateinit var product:Product
//    lateinit var slideAdapter: SliderAdapter
//    lateinit var  blackPics:ArrayList<String>
//    lateinit var  bluePics:ArrayList<String>
//    lateinit var  redPics:ArrayList<String>
//    lateinit var  greenPics:ArrayList<String>
//    lateinit var  yellowPics:ArrayList<String>
//    lateinit var  whitePics:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var product = Gson().fromJson(intent.getStringExtra("product"), Product::class.java)
        val productId=intent.getStringExtra("productId")
        var slideAdapter=SliderAdapter()
        binding.sliderView.setSliderAdapter(slideAdapter)
        binding.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        binding.sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        binding.sliderView.startAutoCycle()

        var bluePics=product.images.get("Blue") as ArrayList<String>
        var redPics=product.images.get("Red") as ArrayList<String>
        var blackPics=product.images.get("Black") as ArrayList<String>
        var yellowPics=product.images.get("Yellow") as ArrayList<String>
        var greenPics=product.images.get("Green") as ArrayList<String>
        var whitePics=product.images.get("White") as ArrayList<String>

        if(redPics.isNotEmpty())
            slideAdapter.updateData(redPics)
        else if(whitePics.isNotEmpty())
            slideAdapter.updateData(whitePics)
        else if(blackPics.isNotEmpty())
            slideAdapter.updateData(blackPics)
        else if(bluePics.isNotEmpty())
            slideAdapter.updateData(bluePics)
        else if(greenPics.isNotEmpty())
            slideAdapter.updateData(greenPics)
        else if(yellowPics.isNotEmpty())
            slideAdapter.updateData(yellowPics)

        binding.colBlack.setOnClickListener(View.OnClickListener { slideAdapter.updateData(blackPics) })
        binding.colBlue.setOnClickListener(View.OnClickListener { slideAdapter.updateData(bluePics) })
        binding.colGreen.setOnClickListener(View.OnClickListener { slideAdapter.updateData(greenPics) })
        binding.colWhite.setOnClickListener(View.OnClickListener { slideAdapter.updateData(whitePics) })
        binding.colRed.setOnClickListener(View.OnClickListener { slideAdapter.updateData(redPics) })
        binding.colYellow.setOnClickListener(View.OnClickListener { slideAdapter.updateData(yellowPics) })

        binding.productName.text=product.title
        binding.buynow.setOnClickListener(View.OnClickListener {
            BuyBtnPressDialog.process(this,this,product,layoutInflater,productId)
        })
        binding.tryon.setOnClickListener(View.OnClickListener {

        })
        binding.otherDetail.text=product.description

        addFeatures(product)
        addReviews(product)
        addAddons(product)


    }
    fun addReviews(product:Product){
        for(rating in product!!.ratings){
            val name=rating["name"] as String
            val star=rating["rating"] as String
            val comment=rating["comment"] as String
            val date=rating["date"] as String

            val viewBinding=CustomViewRatingBinding.inflate(layoutInflater)
            viewBinding.name.text=name
            viewBinding.comment.text=comment
            viewBinding.date.text=date
            viewBinding.ratingBar.rating=star.toFloat()
            binding.reviewsLayout.addView(viewBinding.root)
        }
    }
    fun addFeatures(product:Product){
        var s=String()
        for(f in product!!.features){
            s+=f+"\n"
        }
        binding.features.text=s
    }
    fun addAddons(product: Product){
        val viewBinding=CustomViewAddonBinding.inflate(layoutInflater)

        for(f in product.addons){
            val name=f["Name"] as String
            val price=f["Price"] as String
            val image=f["ImageLink"] as String
            var quantity=f.get("Quantity") as String
            Glide.with(this).load(image).into(viewBinding.addonImage)
            viewBinding.addonPrice.text=price
            viewBinding.adddonName.text=name
            viewBinding.addonAdd.setOnClickListener(View.OnClickListener {

                quantity=(quantity.toInt()+1).toString()
                viewBinding.addonQuantity.text=quantity
                for(a in product.addons){
                    if(a.get("Name") as String==name){
                        a.replace("Quantity",quantity as Objects)
                    }
                }
            })
            viewBinding.addonRemove.setOnClickListener(View.OnClickListener {
                if(quantity.toInt()>=1){
                    quantity=(quantity.toInt()-1).toString()
                    viewBinding.addonQuantity.text=quantity

                    for(a in product.addons){
                        if(a.get("Name") as String==name){
                            a.replace("Quantity",quantity as Objects)
                        }
                    }
                }

            })
            binding.addonLayout.addView(viewBinding.root)
        }
    }
}