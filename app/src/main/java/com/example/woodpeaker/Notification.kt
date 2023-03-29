package com.example.woodpeaker

class Notification(to:String,title:String,body:String) {
    var to:String
    var notification=NotificationData()
//    var title:String
//    var body:String
    init {
        this.to=to
        notification.setData(title,body)
    }
}