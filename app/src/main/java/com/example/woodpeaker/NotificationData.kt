package com.example.woodpeaker

class NotificationData() {
    lateinit var title:String
    lateinit var body:String

    fun setData(title:String,body:String){
        this.title=title
        this.body=body
    }
}