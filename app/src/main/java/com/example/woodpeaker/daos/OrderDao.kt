package com.example.woodpeaker.daos

import com.example.woodpeaker.models.Order
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

object OrderDao {
    val orderCollection=FirebaseDao.db.collection("orders")
    fun addOrder(order: Order): Task<Void> {
        return orderCollection.document().set(order)
    }
    fun getOrder(orderId: String): Task<DocumentSnapshot> {
        return orderCollection.document(orderId).get()
    }
    fun updateOrder(order: Order,id:String): Task<Void> {
        return orderCollection.document(id).set(order)
    }

}