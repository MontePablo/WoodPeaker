package com.example.woodpeaker

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

        binding.package1.setOnClickListener(View.OnClickListener { packageClick(1) })
        binding.package2.setOnClickListener(View.OnClickListener { packageClick(2) })
        binding.package3.setOnClickListener(View.OnClickListener { packageClick(3) })

    }

    fun packageClick(a:Int){
        if(packageList.contains(a)){
            packageList.remove(a)
            colourAsDeselected(a)
        }
        else{
            packageList.add(a)
            colourAsSelected(a)
        }
    }
    fun colourAsSelected(a:Int) {
        when (a) {
            1 -> {
                binding.package1Border.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.bestseller.visibility = View.VISIBLE
            }
            2 -> {
                binding.package2Border.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.bestseller2.visibility = View.VISIBLE
            }
            3 -> {
                binding.package3Border.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.bestseller3.visibility = View.VISIBLE
            }
        }
    }
    fun colourAsDeselected(a:Int) {
        when (a) {
            1 -> {
                binding.package1Border.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                binding.bestseller.visibility = View.INVISIBLE
            }
            2 -> {
                binding.package2Border.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                binding.bestseller2.visibility = View.INVISIBLE
            }
            3 -> {
                binding.package3Border.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                binding.bestseller3.visibility = View.INVISIBLE
            }
        }
    }


}