package com.example.woodpeaker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.woodpeaker.ProductDetail.startActivity
import com.example.woodpeaker.databinding.OrderClickFragViewBinding
import com.example.woodpeaker.models.Order
import com.example.woodpeaker.models.Product
import com.google.gson.Gson
import java.util.*

object OrderClickDialog  {
    lateinit var activity: Activity
    lateinit var product: Product
    lateinit var context:Context
    lateinit var layoutInflater: LayoutInflater

    fun process(
        activity: Activity,
        context: Context,
        product: Product?,
        layoutInflater: LayoutInflater) {

        var order= Order()
        order.shape=product!!.shape
        order.productId=product.productId


        this.activity=activity
        this.product= product!!
        this.context=context
        this.layoutInflater=layoutInflater
        var dialogBuilder= AlertDialog.Builder(context)
        var viewBinding= OrderClickFragViewBinding.inflate(layoutInflater)
        dialogBuilder.setView(viewBinding.root)
        val dialog=dialogBuilder.create()
        dialog.show()

        viewBinding.btnAuto.setOnClickListener(View.OnClickListener {
            val gson = Gson()
            val intent = Intent(context, AutoMeasure::class.java)
            intent.putExtra("product", gson.toJson(product))
            startActivity(intent)
        })
        viewBinding.btnManual.setOnClickListener(View.OnClickListener {
            val gson = Gson()
            val intent = Intent(context, ManualMeasure::class.java)
            intent.putExtra("product", gson.toJson(product))
            startActivity(intent)
        })
        viewBinding.btnQuestion.setOnClickListener(View.OnClickListener {
            WallQuestionDialog.process(activity,context,layoutInflater)
        })
    }

}