package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.woodpeaker.daos.FirebaseDao
import com.example.woodpeaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.shadowlight)
        binding.explore.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, ChooseShape::class.java))
        })
        binding.membership.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, BulkUserPackages::class.java))
        })
        binding.account.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, Profile::class.java))
        })
        binding.orders.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, Orders::class.java))
        })
        binding.help.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, Help::class.java))
        })
        binding.signout.setOnClickListener(View.OnClickListener {
            FirebaseDao.auth.signOut()
            finish()
            startActivity(Intent(this, Login::class.java))
        })

    }
    fun demo(view:View){
        startActivity(Intent(applicationContext,MainActivity2::class.java))
    }
}