package com.example.woodpeaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.woodpeaker.R

class ViewPagerPackageAdapter():RecyclerView.Adapter<ViewPagerPackageAdapter.ViewHolder>() {


    var images:List<Int> =ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_package_images, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }
    fun updateData(pics:List<Int>){
        images=pics
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(images[position])
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val image: ImageView =itemView.findViewById<ImageView>(R.id.package_lv_image)
    }

}