package com.example.woodpeaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.woodpeaker.R
import com.example.woodpeaker.models.Product
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.lang.invoke.ConstantCallSite

class GigsAdapter(options: FirestoreRecyclerOptions<Product>, listener:productFuntions) :
    FirestoreRecyclerAdapter<Product, GigsAdapter.ViewHolder>(options) {
     var listener:productFuntions
    init {
        this.listener=listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_products, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Product) {
        holder.title.text=model.title
        holder.price.text=model.price
        val images=model.images.values as ArrayList<ArrayList<String>>
        Glide.with(holder.image.context).load(images.get(0).get(0)).into(holder.image)

        holder.root.setOnClickListener(View.OnClickListener { listener.productClick(model) })
    }



    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var image=view.findViewById<ImageView>(R.id.item_product_image)
        var title=view.findViewById<TextView>(R.id.item_product_title)
        var price=view.findViewById<TextView>(R.id.item_product_price)
        var root=view.findViewById<ConstraintLayout>(R.id.item_product_root_view)
    }
}
interface productFuntions{
    fun productClick(product:Product)
}
