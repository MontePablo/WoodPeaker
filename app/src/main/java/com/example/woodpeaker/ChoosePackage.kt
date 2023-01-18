package com.example.woodpeaker

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.woodpeaker.databinding.ActivityChoosePackageBinding

class ChoosePackage : AppCompatActivity() {
    lateinit var  binding: ActivityChoosePackageBinding
    var packageList:ArrayList<Int> =ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityChoosePackageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.package1.setOnClickListener(View.OnClickListener { packageClick(1200) })
        binding.package2.setOnClickListener(View.OnClickListener { packageClick(3000) })
        binding.package3.setOnClickListener(View.OnClickListener { packageClick(12000) })
        binding.btnContinue.setOnClickListener(View.OnClickListener { startActivity(Intent(applicationContext, Login::class.java))})

    }

    fun packageClick(a:Int){
        if(packageList.isEmpty()){
            packageList.add(a)
            colourSelected(a)
        }
        else{
            colourDeselected(packageList.get(0))
            packageList.clear()
            packageList.add(a)
            colourSelected(a)
        }
    }
    fun colourSelected(a:Int) {
        when (a) {
            1200 -> {
                binding.package1Border.backgroundTintList=getColorStateList(R.color.dotRedLight)
//                binding.bestseller.visibility = View.VISIBLE
            }
            3000 -> {
                binding.package2Border.backgroundTintList=getColorStateList(R.color.dotRedLight)
//                binding.bestseller2.visibility = View.VISIBLE
            }
            12000 -> {
                binding.package3Border.backgroundTintList=getColorStateList(R.color.dotRedLight)
                binding.bestseller3.visibility = View.VISIBLE
            }
        }
    }
    fun colourDeselected(a:Int) {
        when (a) {
            1200 -> {
                binding.package1Border.backgroundTintList=getColorStateList(R.color.white)
                binding.bestseller.visibility = View.GONE
            }
            3000 -> {
                binding.package2Border.backgroundTintList=getColorStateList(R.color.white)
                binding.bestseller2.visibility = View.GONE
            }
            12000 -> {
                binding.package3Border.backgroundTintList=getColorStateList(R.color.white)
                binding.bestseller3.visibility = View.GONE
            }
        }
    }


}