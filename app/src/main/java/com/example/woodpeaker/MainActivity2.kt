package com.example.woodpeaker

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.woodpeaker.daos.RealtimeDatabaseDao
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging

//import com.google.firebase.messaging.AndroidConfig;
//import com.google.firebase.messaging.AndroidNotification;
//import com.google.firebase.messaging.ApnsConfig;
//import com.google.firebase.messaging.Aps;
//import com.google.firebase.messaging.ApsAlert;
//import com.google.firebase.messaging.BatchResponse;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.MulticastMessage;
//import com.google.firebase.messaging.Notification;
//import com.google.firebase.messaging.SendResponse;
//import com.google.firebase.messaging.TopicManagementResponse;
//import com.google.firebase.messaging.WebpushConfig;
//import com.google.firebase.messaging.WebpushFcmOptions;
//import com.google.firebase.messaging.WebpushNotification;
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    fun btn(view: View) {
//        val notf = Notification(NotificationConstants.to_user, "this is title", "this is body");
//        RetrofitClient.getApiHolder().sendNotification(notf).enqueue(object :
//            Callback<Notification> {
//            override fun onResponse(call: Call<Notification>, response: Response<Notification>) {
//                Log.d("TAG","notification upload success ${response.message()}")
//            }
//
//            override fun onFailure(call: Call<Notification>, t: Throwable) {
//                Log.d("TAG","notification upload failed : ${t.localizedMessage}")
//            }
//        })
//        RealtimeDatabaseDao.reference.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val value = snapshot.value as String
//                Log.d("TAG", "token Value is: " + value)
//                FcmNotificationSend(
//                    value,
//                    "WoodPeaker",
//                    "New Order Arrived! Check Now",
//                    this@MainActivity2
//                )
//                Log.d("TAG",value)
//            }

//            override fun onCancelled(error: DatabaseError) {
//                Log.w("TAG", "Failed to read value.", error.toException())
//            }
//        })
    }
}
