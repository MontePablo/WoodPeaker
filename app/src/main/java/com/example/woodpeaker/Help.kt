package com.example.woodpeaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.woodpeaker.databinding.ActivityHelpBinding

class Help : AppCompatActivity() {
    lateinit var binding:ActivityHelpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.lv345)

        if(binding.email.text.isNotBlank() && binding.msg.text.isNotBlank() && binding.subject.text.isNotBlank()){
            val intent= Intent(Intent.ACTION_SENDTO)
            intent.data= Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL,binding.email.text.toString())
            intent.putExtra(Intent.EXTRA_SUBJECT,binding.subject.text.toString())
            intent.putExtra(Intent.EXTRA_TEXT,binding.msg.text.toString())
            if((intent.resolveActivity(packageManager))!=null){
                startActivity(intent)
            }else{
                Toast.makeText(this,"no app is installed for mailing",Toast.LENGTH_SHORT)
            }
        }
    }
}