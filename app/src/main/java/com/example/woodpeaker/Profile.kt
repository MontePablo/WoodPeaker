package com.example.woodpeaker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.woodpeaker.daos.FirebaseDao.auth
import com.example.woodpeaker.daos.UserDao
import com.example.woodpeaker.databinding.ActivityProfileBinding
import com.example.woodpeaker.models.User


class Profile : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadUserIntoView(auth.uid!!)
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
        if(!email.isNullOrBlank()) {
            user.email = email
        }
        if(!mobile.isNullOrBlank()) {
            user.mobile = mobile
        }
        if(!name.isNullOrBlank()) {
            user.name = name
        }
        UserDao.addUser(user).addOnSuccessListener {
            Toast.makeText(this,"sucess",Toast.LENGTH_SHORT).show()
            Log.d("TAG","user upload success")
            finish()
            startActivity(Intent(this,MainActivity::class.java))
        }.addOnFailureListener {
            Log.d("TAG","user upload failed: ${it.localizedMessage}")
        }
    }
    override fun onBackPressed() {
        startActivity(Intent(this,MainActivity::class.java))
    }
}