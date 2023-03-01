package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.woodpeaker.adapters.ProductsAdapter
import com.example.woodpeaker.adapters.productFuntions
import com.example.woodpeaker.daos.ProductDao
import com.example.woodpeaker.databinding.ActivityProductsBinding
import com.example.woodpeaker.models.Product
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.gson.Gson

class Products : AppCompatActivity(),productFuntions {
    lateinit var binding: ActivityProductsBinding
    lateinit var adapter: ProductsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityProductsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val shape =intent.getStringExtra("shape")!!

        binding.recyclerview.layoutManager=LinearLayoutManager(this)
        val query: Query = ProductDao.productCollection.whereEqualTo("shape",shape).orderBy("price",Query.Direction.ASCENDING)
        val options: FirestoreRecyclerOptions<Product> = FirestoreRecyclerOptions.Builder<Product>().setQuery(query,Product::class.java).build()
        adapter= ProductsAdapter(options,this)
        binding.recyclerview.adapter=adapter

    }


    override fun productClick(product: Product, productId: String) {
        val gson = Gson()
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("product", gson.toJson(product))
        intent.putExtra("productId", productId)
        startActivity(intent)
    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}