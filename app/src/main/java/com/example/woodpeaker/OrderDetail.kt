package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.woodpeaker.daos.ProductDao
import com.example.woodpeaker.databinding.ActivityOrderDetailBinding
import com.example.woodpeaker.databinding.CustomviewProductInOrderdetailBinding
import com.example.woodpeaker.models.Order
import com.example.woodpeaker.models.Product
import com.example.woodpeaker.models.User
import com.google.gson.Gson

class OrderDetail : AppCompatActivity() {
    lateinit var binding: ActivityOrderDetailBinding
    lateinit var order: Order
    lateinit var orderId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        order=Gson().fromJson(intent.getStringExtra("order"), Order::class.java)
        orderId =intent.getStringExtra("orderId")!!
        window.statusBarColor=getColor(R.color.dotRedLight)

        val viewBinding=CustomviewProductInOrderdetailBinding.inflate(layoutInflater)
        binding.productLayout.addView(viewBinding.root)
        viewBinding.price.text=order.price
        viewBinding.quantity.text="1"
        viewBinding.colour.text=order.color
        Glide.with(viewBinding.image.context).load(order.image).into(viewBinding.image)
        viewBinding.image.setOnClickListener(View.OnClickListener {
            ProductDao.getProduct(order.productId).addOnSuccessListener { document->
                val product=document.toObject(Product::class.java)
                val gson = Gson()
                val intent = Intent(this, ProductDetail::class.java)
                intent.putExtra("product", gson.toJson(product))
                startActivity(intent)
            }
        })
        viewBinding.title.text=order.title
        for(f in order.addons){
            val viewBinding=CustomviewProductInOrderdetailBinding.inflate(layoutInflater)
            binding.productLayout.addView(viewBinding.root)
            viewBinding.price.text=f.price
            viewBinding.quantity.text=f.quantity
            Glide.with(viewBinding.image.context).load(f.imageLink).into(viewBinding.image)
            viewBinding.title.text=f.name
        }
        binding.totalPrice.text=order.totalPrice
        binding.price.text=order.price
        binding.addonCount.text=order.addons.size.toString()
        binding.discoutedPrice.text=order.finalPriceAftrDiscnt
        binding.date.text=order.dateTime
        binding.orderid.text=orderId
        binding.updates.text=order.instruction
        binding.address.text=order.address
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}