package com.example.woodpeaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.woodpeaker.databinding.ActivityManualMeasureBinding
import com.example.woodpeaker.models.Product
import com.google.gson.Gson

class ManualMeasure : AppCompatActivity() {
    lateinit var binding:ActivityManualMeasureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityManualMeasureBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var product = Gson().fromJson(intent.getStringExtra("product"), Product::class.java)

        when(product.shape){
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
            calculate(product)

        })
        binding.btnContinue.setOnClickListener(View.OnClickListener {

        })

        binding.btnQuestion.setOnClickListener(View.OnClickListener {
            ManualMeasureQuestionDialog.process(this, this,layoutInflater)
        })

    }
    fun calculate(product: Product){
        when (product.shape){
            "I shape kitchen" ->{
                val c=product.price.toInt() * binding.l2.text.toString().toInt()
                binding.price.text=c.toString()
            }
            "Island shape kitchen" ->{
                val d=binding.l1.text.toString().toInt()+binding.l2.text.toString().toInt()+binding.l3.text.toString().toInt()+binding.l4.text.toString().toInt()
                val c=product.price.toInt() * d
                binding.price.text=c.toString()

            }
            "U shape kitchen" ->{
                val d=binding.l1.text.toString().toInt()+binding.l2.text.toString().toInt()+binding.l3.text.toString().toInt()
                val c=product.price.toInt() * d
                binding.price.text=c.toString()
            }
            "L shape kitchen" ->{
                val d=binding.l1.text.toString().toInt()+binding.l2.text.toString().toInt()
                val c=product.price.toInt() * d
                binding.price.text=c.toString()
            }
        }
    }
}