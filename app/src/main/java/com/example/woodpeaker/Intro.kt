package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.woodpeaker.databinding.ActivityIntroBinding

class Intro : AppCompatActivity() {
    lateinit var binding:ActivityIntroBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val images= listOf(R.drawable.one,R.drawable.two,R.drawable.three)
        binding.viewpager2.adapter=ViewPagerAdapter(images)

        binding.viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                changeLvDotColour();
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                changeLvDotColour();
            }

        })

        binding.exitFromViewpager.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, ChoosePackage::class.java))
        })
    }
    fun changeLvDotColour(){
        when(binding.viewpager2.currentItem){
            0->{
                binding.lv1.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedDeep))
                binding.lv2.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv3.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
            }
            1->{
                binding.lv1.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv2.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedDeep))
                binding.lv3.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
            }
            2->{
                binding.lv1.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv2.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv3.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedDeep))
            }
        }
    }

}