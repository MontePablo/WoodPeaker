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
import com.example.woodpeaker.databinding.ActivityBulkUserPackagesBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class BulkUserPackages : AppCompatActivity(), PaymentResultListener {

    lateinit var binding: ActivityBulkUserPackagesBinding
    var pack3000=false
    var pack12000=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBulkUserPackagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.package1.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this, Login::class.java)
//            intent.putExtra("pack","3000")
//            startActivity(intent)
            payment(3000F)
            pack3000=true
        })
        binding.package2.setOnClickListener(View.OnClickListener {
            payment(12000F)
            pack12000=true
        })
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
        passToNext(p0!!)
    }
    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("TAG","payment failed")
        Toast.makeText(this,"payment failed!$p0", Toast.LENGTH_SHORT).show()
    }
    fun passToNext(id:String){
        val formatter = SimpleDateFormat.getDateInstance()
        val buyDateCalender: Calendar = Calendar.getInstance()
        var buyDate=formatter.format(buyDateCalender.time)

        val expiryDateCalender: Calendar = Calendar.getInstance()
        var expiryDate=formatter.format(expiryDateCalender.time)

        if (pack3000){
            UserDao.user.pack="Merchant1"
            expiryDateCalender.add(Calendar.MONTH,3)
            UserDao.user.run {

            }

        }else if(pack12000){

        }
        order.run {
            var s=binding.adressRadioGroup.checkedRadioButtonId
            if(s!=-1)
                address=findViewById<RadioButton>(s).text.toString()
            paymentId=id
            status="Order Success!"
            val calendar: Calendar = Calendar.getInstance() // Returns instance with current date and time set
            val formatter = SimpleDateFormat("dd-MM-yyyy")
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

    override fun onBackPressed() {
        super.onBackPressed()
        pack3000=false
        pack12000=false
    }
}