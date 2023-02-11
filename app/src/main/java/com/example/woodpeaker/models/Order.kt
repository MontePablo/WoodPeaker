package com.example.woodpeaker.models

import android.widget.ArrayAdapter
import com.example.woodpeakeradmin.models.RoomImages
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Order {
    var price=""
    var orderId=""
    var productId=""
    var title=""
    var dateTime=""
    var image=""
    var status=""
    var clientId=""
    var shape=""
    var lengths=ArrayList<String>()
    var roomImages=RoomImages()
}