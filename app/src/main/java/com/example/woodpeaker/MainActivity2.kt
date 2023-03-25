package com.example.woodpeaker

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.woodpeaker.daos.RealtimeDatabaseDao
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener



class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    fun btn(view: View) {
        RealtimeDatabaseDao.reference.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value as String
                Log.d("TAG", "token Value is: " + value)
                FcmNotificationSend(
                    value,
                    "WoodPeaker",
                    "New Order Arrived! Check Now",
                    this@MainActivity2
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }
}
