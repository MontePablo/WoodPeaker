package com.example.woodpeaker

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.woodpeaker.databinding.ActivityChoosePackageBinding

class ChoosePackage : AppCompatActivity() {
    lateinit var  binding: ActivityChoosePackageBinding
    lateinit var adapter: ViewPagerPackageAdapter
    var flag=0
    var packageList:ArrayList<Int> =ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityChoosePackageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        adapter= ViewPagerPackageAdapter()
        binding.viewpager2?.adapter =adapter

        binding.btnContinue.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, Login::class.java))
        })
        binding.btnContinue.visibility=View.VISIBLE

        binding.singleUser.setOnClickListener(View.OnClickListener { singleUserfunc() })
        binding.bulkUser.setOnClickListener(View.OnClickListener { bulkUserfunc() })

        binding.viewpager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                changeSubcribebutton();
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                changeSubcribebutton();
            }

        })




        }
    fun singleUserfunc() {
        binding.chooseUserLayout.visibility=View.GONE
        val images= listOf<Int>(R.drawable.lowpackage)
        adapter.updateData(images)
        binding.viewpager2?.visibility=View.VISIBLE
        flag=1
        backpresscount=0
        binding.btnContinue.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, Login::class.java))
        })
        binding.btnContinue.visibility=View.VISIBLE

    }

    fun bulkUserfunc(){
        binding.chooseUserLayout.visibility=View.GONE
        val images= listOf<Int>(R.drawable.midpackage,R.drawable.highpackage)
        adapter.updateData(images)
        binding.viewpager2?.visibility=View.VISIBLE
        flag=2
        backpresscount=0
        binding.btnContinue.visibility=View.VISIBLE

    }



    fun changeSubcribebutton(){
        when(binding.viewpager2?.currentItem){
            0->{
                if(flag==1) {
                    // send data to intent according package choosed
                }else if(flag==2) {
                    // send data to intent according package choosed
                }
                else{
                }
            }
            1-> {

            }
        }
    }
    var backpresscount=0
        override fun onBackPressed() {
        backpresscount++
        if(backpresscount<=1){
                binding.chooseUserLayout.visibility=View.VISIBLE
                binding.viewpager2?.visibility=View.GONE
//                binding.package1Border.visibility=View.GONE
//                binding.package2Border.visibility=View.GONE
//                binding.package3Border.visibility=View.GONE
//                binding.btnContinue.visibility=View.GONE
        }else{
            super.onBackPressed()
            backpresscount=0
        }
    }



//        binding.singleUser.setOnClickListener(View.OnClickListener {
//            backpresscount=0
//            binding.chooseUserLayout.visibility=View.GONE
//            binding.package3Border.visibility=View.VISIBLE
//            binding.package2Border.visibility=View.GONE
//            binding.package1Border.visibility=View.GONE
//            packageClick(1200)
//        })
//
//        binding.bulkUser.setOnClickListener(View.OnClickListener {
//            backpresscount=0
//            binding.chooseUserLayout.visibility=View.GONE
//            binding.package1Border.visibility=View.VISIBLE
//            binding.package2Border.visibility=View.VISIBLE
//            binding.package3Border.visibility=View.GONE
//        })
//
//        binding.package1.setOnClickListener(View.OnClickListener { packageClick(3000) })
//        binding.package2.setOnClickListener(View.OnClickListener { packageClick(12000) })
//        binding.package3.setOnClickListener(View.OnClickListener { packageClick(1200) })
//        binding.btnContinue.setOnClickListener(View.OnClickListener { startActivity(Intent(applicationContext, Login::class.java))})
//
//    }
//
//    var backpresscount=0
//    override fun onBackPressed() {
//        backpresscount++
//        if(backpresscount<=1){
//                binding.chooseUserLayout.visibility=View.VISIBLE
//                binding.package1Border.visibility=View.GONE
//                binding.package2Border.visibility=View.GONE
//                binding.package3Border.visibility=View.GONE
//                binding.btnContinue.visibility=View.GONE
//        }else{
//            super.onBackPressed()
//            backpresscount=0
//        }
//    }
//
//    fun packageClick(a:Int){
//        if(packageList.isEmpty()){
//            packageList.add(a)
//            colourSelected(a)
//        }
//        else{
//            colourDeselected(packageList.get(0))
//            packageList.clear()
//            packageList.add(a)
//            colourSelected(a)
//        }
//    }
//    fun colourSelected(a:Int) {
//        when (a) {
//            1200 -> {
//                binding.package3Border.backgroundTintList=getColorStateList(R.color.dotRedLight)
//                binding.btnContinue.visibility=View.VISIBLE
//            }
//            3000 -> {
//                binding.package1Border.backgroundTintList=getColorStateList(R.color.dotRedLight)
//                binding.btnContinue.visibility=View.VISIBLE
//            }
//            12000 -> {
//                binding.package2Border.backgroundTintList=getColorStateList(R.color.dotRedLight)
//                binding.btnContinue.visibility=View.VISIBLE
//            }
//        }
//    }
//    fun colourDeselected(a:Int) {
//        when (a) {
//            1200 -> {
//                binding.package3Border.backgroundTintList=getColorStateList(R.color.white)
//                binding.bestseller3.visibility = View.GONE
//                binding.btnContinue.visibility=View.GONE
//            }
//            3000 -> {
//                binding.package1Border.backgroundTintList=getColorStateList(R.color.white)
//                binding.bestseller1.visibility = View.GONE
//                binding.btnContinue.visibility=View.GONE
//            }
//            12000 -> {
//                binding.package2Border.backgroundTintList=getColorStateList(R.color.white)
//                binding.bestseller2.visibility = View.GONE
//                binding.btnContinue.visibility=View.GONE
//            }
//        }
    }




