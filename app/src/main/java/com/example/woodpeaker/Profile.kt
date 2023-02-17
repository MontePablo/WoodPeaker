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
    lateinit var pack:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadUserIntoView(auth.uid!!)
        pack=intent.getStringExtra("pack")!!
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
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("pack",pack)
            startActivity(intent)
            finish()
        }.addOnFailureListener {
            Log.d("TAG","user upload failed: ${it.localizedMessage}")
        }
    }
    override fun onBackPressed() {
        startActivity(Intent(this,MainActivity::class.java))
    }
}