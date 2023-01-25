package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.woodpeaker.databinding.ActivityCustomerTypeBinding

class CustomerType : AppCompatActivity() {
    lateinit var  binding: ActivityCustomerTypeBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityCustomerTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.singleUser.setOnClickListener(View.OnClickListener { singleUserfunc() })
        binding.bulkUser.setOnClickListener(View.OnClickListener { bulkUserfunc() })


        }
    fun singleUserfunc() {
        startActivity(Intent(applicationContext, Login::class.java))
    }

    fun bulkUserfunc(){
        startActivity(Intent(applicationContext, SelectYourPackage::class.java))
        binding.chooseUserLayout.visibility=View.GONE
    }


}




