package com.example.woodpeaker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.woodpeaker.daos.StorageDao
import com.example.woodpeaker.databinding.ActivityManualMeasureBinding
import com.example.woodpeaker.databinding.CustomviewImageBinding
import com.example.woodpeaker.models.Order
import com.google.gson.Gson
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import kotlin.concurrent.timerTask

class ManualMeasure : AppCompatActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isReadPermissionGranted = false

    var imageViewTable: Hashtable<Int, CustomviewImageBinding> = Hashtable<Int,CustomviewImageBinding>()
    lateinit var binding:ActivityManualMeasureBinding
    lateinit var order:Order
    var lengthDatas:ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityManualMeasureBinding.inflate(layoutInflater)
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            isReadPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
        }
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.lv345)
        permission()
        order = Gson().fromJson(intent.getStringExtra("order"), Order::class.java)
        if(intent.getStringArrayListExtra("lengthDatas") !=null)
            lengthDatas.addAll(intent.getStringArrayListExtra("lengthDatas")!!)
//        val arr=intent.getStringArrayListExtra("lengthDatas")!!
        Log.d("TAG",order.image+"@@"+order.shape+order.price)

        if(lengthDatas.isNotEmpty()){
            if(lengthDatas.size==1){
                binding.l2.setText(lengthDatas.get(1))
            }else if(lengthDatas.size==2){
                binding.l1.setText(lengthDatas.get(0))
                binding.l2.setText(lengthDatas.get(1))
            }else{
                binding.l1.setText(lengthDatas.get(0))
                binding.l2.setText(lengthDatas.get(1))
                binding.l3.setText(lengthDatas.get(2))
            }
        }


        when(order.shape){
            "I shape kitchen" ->{
                binding.l2.visibility=View.VISIBLE
                binding.l2foot.visibility=View.VISIBLE
            }
            "Island shape kitchen" ->{
                binding.l1.visibility=View.VISIBLE
                binding.l2.visibility=View.VISIBLE
                binding.l3.visibility=View.VISIBLE
                binding.l1foot.visibility=View.VISIBLE
                binding.l2foot.visibility=View.VISIBLE
                binding.l3foot.visibility=View.VISIBLE

            }
            "U shape kitchen" ->{
                binding.l1.visibility=View.VISIBLE
                binding.l2.visibility=View.VISIBLE
                binding.l3.visibility=View.VISIBLE
                binding.l1foot.visibility=View.VISIBLE
                binding.l2foot.visibility=View.VISIBLE
                binding.l3foot.visibility=View.VISIBLE

            }
            "L shape kitchen" ->{
                binding.l1.visibility=View.VISIBLE
                binding.l2.visibility=View.VISIBLE
                binding.l1foot.visibility=View.VISIBLE
                binding.l2foot.visibility=View.VISIBLE

            }
        }

        binding.calculate.setOnClickListener(View.OnClickListener {
            calculate()
        })
        binding.btnContinue.setOnClickListener(View.OnClickListener {
            getImages()
            val gson = Gson()
            val intent = Intent(applicationContext, FinalOrderPage::class.java)
            intent.putExtra("order", gson.toJson(order))
            startActivity(intent)
        })
        binding.addImage.setOnClickListener(View.OnClickListener { addImage() })


    }

    private fun getImages() {
        for(f in imageViewTable){
            order.roomImages.ImageName.add(f.value.storeName.text.toString())
            order.roomImages.ImageLink.add(f.value.storeLink.text.toString())
        }
    }

    fun calculate(){
        var l2=0F
        if(binding.l2.text.isNotEmpty())
            l2=binding.l2.text.toString().toFloat()
        var l1=0F
        if(binding.l1.text.isNotEmpty())
            l1=binding.l1.text.toString().toFloat()
        var l3=0F
        if(binding.l3.text.isNotEmpty())
            l3=binding.l3.text.toString().toFloat()
        binding.price.visibility=View.VISIBLE
        when (order.shape){
            "I shape kitchen" ->{
                val c=order.price.toFloat() * l2
                binding.price.text=c.toString()
                order.lengths.add(l2.toString())
            }
            "Island shape kitchen" ->{
                val d=l1+l2+l3+l2 -4

                val c=order.price.toFloat() * d
                binding.price.text=c.toString()
                order.lengths.add(l1.toString())
                order.lengths.add(l2.toString())
                order.lengths.add(l3.toString())
            }
            "U shape kitchen" ->{
                val d=l1+l2+l3 -2
                val c=order.price.toFloat() * d
                binding.price.text=c.toString()
                order.lengths.add(l1.toString())
                order.lengths.add(l2.toString())
                order.lengths.add(l3.toString())

            }
            "L shape kitchen" ->{
                val d=l1+l2  -1
                val c=order.price.toFloat() * d
                binding.price.text=c.toString()
                order.lengths.add(l1.toString())
                order.lengths.add(l2.toString())

            }
        }
    }
    private fun progressBarFunc(viewBinding: CustomviewImageBinding){
        var counter=0
        var timer=Timer()
        var timertask= timerTask {
            run(){
                super.runOnUiThread(Runnable {
                    viewBinding.progressBar.visibility=View.VISIBLE
                    counter++;
                    viewBinding.progressBar.progress = counter
                    if(counter==100){
                        timer.cancel()
                        viewBinding.progressBar.visibility=View.INVISIBLE
                    }
                })
            }
        }
        timer.schedule(timertask,0,15)
    }
    fun deleteImagesFromCloud(link:String){
        if(link.isNotBlank()){
            StorageDao.deleteProductImage(link).addOnFailureListener {
                Log.d("TAG", "Delete failed:${it.localizedMessage}")
            }
        }else {
            for (f in imageViewTable) {
                StorageDao.deleteProductImage(f.value.storeName.text.toString())
                    .addOnFailureListener {
                        Log.d("TAG", "Delete failed:${it.localizedMessage}")
                    }
            }
        }
    }
    private fun addImage() {
        val imageBinding=CustomviewImageBinding.inflate(layoutInflater)
//        storagePermission()
        imageBinding.delete.setOnClickListener(View.OnClickListener {
            imageViewTable.remove(imageBinding.hashCode())
            binding.imageLayout.removeView(imageBinding.root)
            deleteImagesFromCloud(imageBinding.storeName.text.toString())
        })
        imageBinding.retry.setOnClickListener(View.OnClickListener {
            val imageUri = Uri.parse(imageBinding.storeUri.text.toString())
            CoroutineScope(Dispatchers.Default).launch { uploadImage(imageBinding, imageUri) }
            imageBinding.retry.visibility = View.INVISIBLE
            binding.retryToast.visibility = View.INVISIBLE
            imageBinding.imageview.setImageURI(imageUri) })

        imageBinding.insert.setOnClickListener(View.OnClickListener {
            photoPick(imageBinding.hashCode())
            imageViewTable.put(imageBinding.hashCode(),imageBinding)
        })
        binding.imageLayout.addView(imageBinding.root)
    }
    fun photoPick(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            Log.d("TAG","onActivityResult: Failed")
            return
        }
        else if (requestCode == 2000) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                } else {
                }
            }
        }
        else{
            var imageUri=data!!.data
            Log.d("TAG","onActivityResult Image received")
            val imageBinding= imageViewTable[requestCode]
//            imageBinding?.imageview?.setImageURI(imageUri)
            imageBinding?.storeUri?.text = imageUri.toString()
            imageBinding?.insert?.visibility=View.GONE
            CoroutineScope(Dispatchers.Default).launch {
                uploadImage(imageBinding!!,imageUri!!)
            }
            return
        }
    }
    suspend fun uploadImage(viewBinding:CustomviewImageBinding,imageUri: Uri){
        progressBarFunc(viewBinding)
        val imageFile= File(RealPathUtil.getRealPath(this, imageUri))
        val compressedImage = Compressor.compress(this, imageFile){
            default(720,1280, Bitmap.CompressFormat.JPEG,60)
        }
        Log.d("TAG","orginalSize: ${imageFile.length()} compressedSize:${compressedImage.length()}")
        val fileName = imageUri.hashCode().toString()
        StorageDao.uploadProductImage(compressedImage.toUri(), fileName)!!.addOnSuccessListener {
            Log.d("TAG","upload success")
            viewBinding.imageview.setImageURI(imageUri)
            StorageDao.getImageUrlOfProduct(fileName)!!.addOnSuccessListener {
                val imageLink=it.toString()
                viewBinding.storeLink.setText(imageLink)
                viewBinding.storeName.setText(fileName)
                Log.d("TAG","getting Url success ${imageLink}")
            }
        }.addOnFailureListener {
            Log.d("TAG","uploadImage onFailure: ${it.localizedMessage}")
            viewBinding.retry.visibility=View.VISIBLE
            viewBinding.imageview.setImageResource(R.drawable.logo_retry_arrow)
            binding.retryToast.visibility=View.VISIBLE
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
}