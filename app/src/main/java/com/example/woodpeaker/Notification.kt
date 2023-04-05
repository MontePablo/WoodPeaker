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
class NotificationData() {
    lateinit var title:String
    lateinit var body:String

    fun setData(title:String,body:String){
        this.title=title
        this.body=body
    }
}