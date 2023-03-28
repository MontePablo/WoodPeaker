package com.example.woodpeaker

class Notification(to:String,title:String,body:String) {
    var to:String
    var data=NotificationData()
//    var title:String
//    var body:String
    init {
        this.to=to
        data.setData(title,body)
    }
}