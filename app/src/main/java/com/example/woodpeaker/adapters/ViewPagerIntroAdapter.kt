package com.example.woodpeaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.woodpeaker.R

class ViewPagerIntroAdapter(val images:List<Int>):RecyclerView.Adapter<ViewPagerIntroAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_intro_images, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(images[position])
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val image: ImageView =itemView.findViewById<ImageView>(R.id.intro_lv_image)
    }

}