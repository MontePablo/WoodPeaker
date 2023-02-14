package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.woodpeaker.daos.OrderDao
import com.example.woodpeaker.daos.UserDao
import com.example.woodpeaker.databinding.ActivityFinalOrderPageBinding
import com.example.woodpeaker.models.Order
import com.google.gson.Gson
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class FinalOrderPage : AppCompatActivity(), PaymentResultListener {
    lateinit var binding: ActivityFinalOrderPageBinding
    lateinit var order:Order
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFinalOrderPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        order = Gson().fromJson(intent.getStringExtra("order"), Order::class.java)
        loadData()
        binding.btnNext.setOnClickListener(View.OnClickListener {
            payment(order.finalPriceAftrDiscnt.toFloat())
        })
    }
    fun loadData(){
        binding.price.text=order.price
        var addonPrice=0.0
        var discountedPrice=0.0
            for(f in order.addons){
            addonPrice+=f.price.toInt()
        }
        binding.addonPrice.text=addonPrice.toString()
        var totalPrice=addonPrice+order.price.toInt()
        binding.totalPrice.text=totalPrice.toString()
        order.totalPrice=totalPrice.toString()
        if(order.pack=="Merchant1"){
            discountedPrice=totalPrice * 0.92
        }else if(order.pack=="Merchant2"){
            discountedPrice=totalPrice * 0.85
        }else{
            discountedPrice=totalPrice
        }
        binding.discountedPrice.text=discountedPrice.toString()
        order.finalPriceAftrDiscnt=discountedPrice.toString()
        for(f in UserDao.user.Adresses){
            var radioButton=RadioButton(this)
            radioButton.text=f
            radioButton.setBackgroundResource(R.drawable.shape_corner15dp_strokeshadow1dp)
            binding.adressRadioGroup.addView(radioButton)
        }
    }


    fun payment(amount:Float){
        val co = Checkout()
        co.setKeyID("rzp_test_xTHEX7CRZJHVvn")
        try {
            val options = JSONObject()
            options.put("name", "WoodPeaker")
            options.put("description", "Order Amount")
            options.put("send_sms_hash", true)
            options.put("allow_rotation", true)
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://i.postimg.cc/HkD198y8/logo.png")
            options.put("currency", "INR")
            options.put("amount", amount*100)
            val preFill = JSONObject()
            preFill.put("email", "test@razorpay.com")
            preFill.put("contact", "9876543210")
            options.put("prefill", preFill)
            co.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_SHORT)
                .show()
            Log.d("TAG","error in payment ${e.localizedMessage}")
            e.printStackTrace()
        }
        Log.d("TAG","pay func finished")

    }
    override fun onPaymentSuccess(p0: String?) {
        Log.d("TAG","payment success")
        Toast.makeText(this,"payment done! $p0", Toast.LENGTH_SHORT).show()
        passOrder(p0!!)
    }
    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("TAG","payment failed")
        Toast.makeText(this,"payment failed!$p0", Toast.LENGTH_SHORT).show()
    }
    fun passOrder(id:String){
        var order=Order()
        order.run {
            paymentId=id
            status="Order Success!"
            val calendar: Calendar = Calendar.getInstance() // Returns instance with current date and time set
            val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
            dateTime=formatter.format(calendar.time)
        }
        OrderDao.addOrder(order)
            .addOnSuccessListener {
                Toast.makeText(this, "order complete!", Toast.LENGTH_SHORT).show()
                Log.d("TAG","order upload success")
            }.addOnFailureListener {
                Log.d("TAG","order upload failed ${it.localizedMessage}")
            }
        ContextCompat.startActivity(this, Intent(this, Orders::class.java), null)
    }
}