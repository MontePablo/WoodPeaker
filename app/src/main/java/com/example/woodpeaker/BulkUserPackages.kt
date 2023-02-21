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

class BulkUserPackages : AppCompatActivity() {

    lateinit var binding: ActivityBulkUserPackagesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBulkUserPackagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.package1.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Login::class.java)
            intent.putExtra("pack","3000")
            startActivity(intent)

        })
        binding.package2.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Login::class.java)
            intent.putExtra("pack","3000")
            startActivity(intent)
        })
    }
}