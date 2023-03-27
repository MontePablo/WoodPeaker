package com.example.woodpeaker

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    lateinit var retrofitApiHolder:RetrofitApiHolder
    init {
        val retrofit=Retrofit.Builder().baseUrl(NotificationConstants.postUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
        retrofitApiHolder=retrofit.create(RetrofitApiHolder::class.java)
    }
    fun getApiHolder(): RetrofitApiHolder {
        return retrofitApiHolder
    }

}