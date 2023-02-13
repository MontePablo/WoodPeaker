package com.example.woodpeaker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.woodpeaker.daos.StorageDao
import com.example.woodpeaker.databinding.ActivityManualMeasureBinding
import com.example.woodpeaker.databinding.CustomviewImageBinding
import com.example.woodpeaker.models.Order
import com.google.gson.Gson
import com.razorpay.Checkout
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timerTask

class ManualMeasure : AppCompatActivity() {
    var imageViewTable: Hashtable<Int, CustomviewImageBinding> = Hashtable<Int,CustomviewImageBinding>()
    lateinit var binding:ActivityManualMeasureBinding
    lateinit var order:Order
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityManualMeasureBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        order = Gson().fromJson(intent.getStringExtra("order"), Order::class.java)

        when(order.shape){
            "I shape kitchen" ->{
                binding.l2layout.visibility=View.VISIBLE
            }
            "Island shape kitchen" ->{
                binding.l1layout.visibility=View.VISIBLE
                binding.l2layout.visibility=View.VISIBLE
                binding.l3layout.visibility=View.VISIBLE
                binding.l4layout.visibility=View.VISIBLE

            }
            "U shape kitchen" ->{
                binding.l1layout.visibility=View.VISIBLE
                binding.l2layout.visibility=View.VISIBLE
                binding.l3layout.visibility=View.VISIBLE
            }
            "L shape kitchen" ->{
                binding.l1layout.visibility=View.VISIBLE
                binding.l2layout.visibility=View.VISIBLE
            }
        }

        binding.calculate.setOnClickListener(View.OnClickListener {
            calculate()
        })
        binding.btnContinue.setOnClickListener(View.OnClickListener {
            getImages()

        })
        binding.btnQuestion.setOnClickListener(View.OnClickListener {
            ManualMeasureQuestionDialog.process(this, this,layoutInflater)
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
        val l1=binding.l1.text.toString().toInt()
        val l2=binding.l2.text.toString().toInt()
        val l3=binding.l3.text.toString().toInt()
        val l4=binding.l4.text.toString().toInt()
        when (order.shape){
            "I shape kitchen" ->{
                val c=order.price.toInt() * l2
                binding.price.text=c.toString()
                order.lengths.add(l2.toString())
            }
            "Island shape kitchen" ->{
                val d=l1+l2+l3+l4
                val c=order.price.toInt() * d
                binding.price.text=c.toString()
                order.lengths.add(l1.toString())
                order.lengths.add(l2.toString())
                order.lengths.add(l3.toString())
                order.lengths.add(l4.toString())
            }
            "U shape kitchen" ->{
                val d=l1+l2+l3
                val c=order.price.toInt() * d
                binding.price.text=c.toString()
                order.lengths.add(l1.toString())
                order.lengths.add(l2.toString())
                order.lengths.add(l3.toString())

            }
            "L shape kitchen" ->{
                val d=l1+l2
                val c=order.price.toInt() * d
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
}