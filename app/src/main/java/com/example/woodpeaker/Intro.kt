package com.example.woodpeaker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.woodpeaker.adapters.ViewPagerIntroAdapter
import com.example.woodpeaker.databinding.ActivityIntroBinding

class Intro : AppCompatActivity() {
    lateinit var binding:ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.lv345)
        permission()
        val images= listOf(R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.five)
        binding.viewpager2.adapter= ViewPagerIntroAdapter(images)

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
    fun permission() {
        if (!checkPermission()){
            showPermissionDialog()
        }
    }
    private fun showPermissionDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(
                    String.format(
                        "package:%s", *arrayOf<Any>(
                            applicationContext.packageName
                        )
                    )
                )
                startActivityForResult(intent, 2000)
            } catch (e: Exception) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivityForResult(intent, 2000)
            }
        } else ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            333
        )
    }

    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val write = ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            val read = ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            write == PackageManager.PERMISSION_GRANTED &&
                    read == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 333) {
            if (grantResults.size > 0) {
                val write = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val read = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (read && write) {
                } else {
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2000) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                } else {
                }
            }
        }
    }
}