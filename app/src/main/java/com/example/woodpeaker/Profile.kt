package com.example.woodpeaker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.woodpeaker.daos.FirebaseDao.auth
import com.example.woodpeaker.daos.UserDao
import com.example.woodpeaker.databinding.ActivityProfileBinding
import com.example.woodpeaker.models.User
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class Profile : AppCompatActivity(), PaymentResultListener {
    lateinit var binding: ActivityProfileBinding
    lateinit var user: User
    var pack3000=false
    var pack12000=false
    var pack=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadUserIntoView(auth.uid!!)
        pack=intent.getStringExtra("pack").toString()
        window.statusBarColor=getColor(R.color.lv345)
        binding.save.setOnClickListener(View.OnClickListener { upload() })

    }
     fun loadUserIntoView(uid: String) {
        UserDao.getUser(uid).addOnSuccessListener { document->
            document.toObject(User::class.java)?.let { user ->
                this.user=user
                binding.name.setText(user.name)
                binding.mobile.setText(user.mobile)
                binding.email.setText(user.email)
            }
        }
    }
    private fun upload(){
        var email=binding.email.text.toString()
        var mobile=binding.mobile.text.toString()
        var name=binding.name.text.toString()
        if(!email.isNullOrBlank() && !mobile.isNullOrBlank() && !name.isNullOrBlank()) {
            user.email = email
            user.mobile = mobile
            user.name = name
        }else{
            Toast.makeText(this,"fill all fields first",Toast.LENGTH_SHORT)
        }
        UserDao.addUser(user).addOnSuccessListener {
            Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()
            Log.d("TAG","user upload success")
            val intent:Intent
            if(pack.isEmpty()){
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                if(pack=="Package1"){
                    payment(3000F)
                    pack3000=true
                }else if(pack=="Package2"){
                    payment(12000F)
                    pack12000=true
                }
            }


        }.addOnFailureListener {
            Log.d("TAG","user upload failed: ${it.localizedMessage}")
        }
    }
    override fun onBackPressed() {
        startActivity(Intent(this,MainActivity::class.java))
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
        UserDao.user.packagePaymentId=id
        val formatter = SimpleDateFormat.getDateInstance()
        val buyDateCalender: Calendar = Calendar.getInstance()
        val buyDate=formatter.format(buyDateCalender.time)
        var expiryDateCalender: Calendar = Calendar.getInstance()
        if (pack3000){
            expiryDateCalender.add(Calendar.MONTH,3)
            val expiryDate=formatter.format(expiryDateCalender.time)
            UserDao.user.run {
                pack="Merchant1"
                packBuyDate=buyDate
                packExpiryDate=expiryDate
            }
        }else if(pack12000){
            UserDao.user.pack="Merchant2"
            expiryDateCalender.add(Calendar.MONTH,12)
            val expiryDate=formatter.format(expiryDateCalender.time)
            UserDao.user.run {
                pack="Merchant2"
                packBuyDate=buyDate
                packExpiryDate=expiryDate
            }
        }
        UserDao.updateUser()
            .addOnSuccessListener {
                Toast.makeText(this, "success!", Toast.LENGTH_SHORT).show()
                Log.d("TAG","success")
                startActivity(Intent(this,MainActivity::class.java))
            }.addOnFailureListener {
                Log.d("TAG","failed ${it.localizedMessage} \n try again later")
            }
        startActivity(Intent(this,MainActivity::class.java))
     }

}