package com.example.woodpeaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.woodpeaker.R
import com.example.woodpeaker.models.Order
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.ObservableSnapshotArray


class OrderAdapter(options: FirestoreRecyclerOptions<Order>, listener:orderFunctions) :
    FirestoreRecyclerAdapter<Order,OrderAdapter.ViewHolder>(options) {
    var listener:orderFunctions
    init {
        this.listener=listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Order) {

        val snapshots: ObservableSnapshotArray<Order> = snapshots
        val orderId = snapshots.getSnapshot(holder.bindingAdapterPosition).id
        holder.title.text=model.title
        holder.review.setOnClickListener(View.OnClickListener {  })
        holder.status.text=model.status.lines().last()
        holder.dateTime.text=model.dateTime
        Glide.with(holder.image.context).load(model.image).into(holder.image)
        holder.root.setOnClickListener(View.OnClickListener {listener.orderClick(model,orderId)})

    }



    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var image=view.findViewById<ImageView>(R.id.item_order_image)
        var dateTime=view.findViewById<TextView>(R.id.item_order_dateTime)
        var review=view.findViewById<Button>(R.id.item_order_review)
        var status=view.findViewById<TextView>(R.id.item_order_status)
        var title=view.findViewById<TextView>(R.id.item_order_title)
        var root=view.findViewById<ConstraintLayout>(R.id.item_order_root_view)
    }
}
interface orderFunctions{
    fun orderClick(order: Order, orderId: String)
}
