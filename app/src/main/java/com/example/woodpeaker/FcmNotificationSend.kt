package com.example.woodpeaker

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class FcmNotificationSend(userToken:String,title:String,body:String,activity:AppCompatActivity)  {

//    lateinit var userToken: String
//    lateinit var title: String
//    lateinit var body: String
    var postUrl="https://fcm.googleapis.com/fcm/send"
    val fcmServerKey="AAAAIBHAIqs:APA91bFu4j27lXVFEh2wlpEwGnUqzcOomRRjyl3eMdioFR0rvDuTPkKnHVc-zEA-LhFtwEuCMpxAEji8mANr98XWko-SKjhem4EHZQc9WOL5Qe2CIBv_pXuK_Us8e_wCnbv6WkouC3HY"

    init {
        val requestQueue=Volley.newRequestQueue(activity)
        val mainObj=JSONObject()
        try{
            mainObj.put("to",userToken)
            val dataobj=JSONObject()
            dataobj.put("title",title)
            dataobj.put("body",body)
//            dataobj.put("icon","")
            mainObj.put("notification",dataobj)

            val request=JsonObjectRequest(com.android.volley.Request.Method.POST,postUrl,mainObj,Response.Listener<JSONObject>{ Log.d("TAG",it.toString())
            },Response.ErrorListener {Log.d("TAG",it.toString())}){

            }

//            request.headers["Content-Type"] = "application/json"
//            request.headers["Authorization"] = fcmServerKey
            requestQueue.add(request)

        }finally {
            Log.d("TAG","error on post method of notification")
        }





    }



}