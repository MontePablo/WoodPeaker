package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.woodpeaker.adapters.OrderAdapter
import com.example.woodpeaker.adapters.orderFunctions
import com.example.woodpeaker.daos.FirebaseDao
import com.example.woodpeaker.daos.OrderDao
import com.example.woodpeaker.databinding.ActivityOrdersBinding
import com.example.woodpeaker.models.Order
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.gson.Gson

class Orders : AppCompatActivity(), orderFunctions {
    lateinit var binding: ActivityOrdersBinding
    lateinit var adapter: OrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TAG","order start")
        super.onCreate(savedInstanceState)
        binding=ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.lv345)
//        binding.recyclerview.layoutManager= LinearLayoutManager(this)

//    OrderDao.orderCollection.whereEqualTo("clientId",FirebaseDao.auth.uid).orderBy("dateTime",Query.Direction.ASCENDING).get().addOnSuccessListener {
//            Log.d("TAG","size"+it.documents.size.toString())
//            for(f in it.documents){
//                Log.d("TAG","id=="+f.id)
//                val g=f.toObject(Order::class.java)
//                Log.d("TAG","name=="+g!!.title)
//                Log.d("TAG","price=="+g!!.price)
//
//            }
//        }
        binding.recyclerview.setLayoutManager(
            WrapContentLinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )

        val query: Query = OrderDao.orderCollection.whereEqualTo("clientId",FirebaseDao.auth.uid).orderBy("dateTime",Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<Order> = FirestoreRecyclerOptions.Builder<Order>().setQuery(query, Order::class.java).build()
        adapter= OrderAdapter(options,this)
        binding.recyclerview.adapter=adapter
        Log.d("TAG","order end")
        Log.d("TAG"," cliend id:"+ FirebaseDao.auth.uid)
    }

    override fun orderClick(order: Order, orderId: String) {
        val gson = Gson()
        val intent = Intent(applicationContext, OrderDetail::class.java)
        intent.putExtra("order", gson.toJson(order))
        intent.putExtra("orderId", orderId)
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