package com.example.woodpeaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.woodpeaker.databinding.ActivityIntroBinding

class Intro : AppCompatActivity() {
    lateinit var binding:ActivityIntroBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val images= listOf(R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.five)
        binding.viewpager2.adapter=ViewPagerIntroAdapter(images)

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
            startActivity(Intent(applicationContext, CustomerType::class.java))
        })
    }
    fun changeLvDotColour(){
        when(binding.viewpager2.currentItem){
            0->{
                window.statusBarColor=getColor(R.color.lv1)
                binding.lv1.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedDeep))
                binding.lv2.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv3.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv4.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.exitFromViewpager.visibility=View.GONE
            }
            1->{
                window.statusBarColor=getColor(R.color.lv2)
                binding.lv1.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv2.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedDeep))
                binding.lv3.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv4.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.exitFromViewpager.visibility=View.GONE
            }
            2->{
                window.statusBarColor=getColor(R.color.lv345)
                binding.lv1.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv2.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv3.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedDeep))
                binding.lv4.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.exitFromViewpager.visibility=View.GONE

            }
            3->{
                window.statusBarColor=getColor(R.color.lv345)
                binding.lv1.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv2.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv3.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv4.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedDeep))
                binding.exitFromViewpager.visibility=View.VISIBLE
            }
            4->{
                window.statusBarColor=getColor(R.color.lv345)
                binding.lv1.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv2.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv3.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.lv4.setBackgroundColor(applicationContext.resources.getColor(R.color.dotRedLight))
                binding.exitFromViewpager.visibility=View.VISIBLE
            }
        }
    }

}