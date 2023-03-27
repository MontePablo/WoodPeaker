package com.example.woodpeaker

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitApiHolder {


    @Headers("Authorization: ${NotificationConstants.fcm_server_key}","Content-Type: ${NotificationConstants.content_type}")
    @POST("fcm/send")
    fun sendNotification(@Body Notification: Notification ): Call<Notification>

}

