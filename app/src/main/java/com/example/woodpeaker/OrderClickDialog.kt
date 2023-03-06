package com.example.woodpeaker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.example.woodpeaker.databinding.OrderClickFragViewBinding
import com.example.woodpeaker.models.Order
import com.google.gson.Gson

object OrderClickDialog  {
    lateinit var activity: Activity
    lateinit var order: Order
    lateinit var context:Context
    lateinit var layoutInflater: LayoutInflater

    fun process(
        activity: Activity,
        context: Context,
        order: Order?,
        layoutInflater: LayoutInflater) {

        this.activity=activity
        this.order= order!!
        this.context=context
        this.layoutInflater=layoutInflater
        var dialogBuilder= AlertDialog.Builder(context)
        var viewBinding= OrderClickFragViewBinding.inflate(layoutInflater)
        dialogBuilder.setView(viewBinding.root)
        val dialog=dialogBuilder.create()
        dialog.show()

        viewBinding.btnAuto.setOnClickListener(View.OnClickListener {
            if(viewBinding.radioGroupNowall.checkedRadioButtonId!=-1) {
                when (viewBinding.radioGroupNowall.checkedRadioButtonId) {
                    viewBinding.allSideWallAvail.id -> order.wallDesign = "All side wall present"
                    viewBinding.leftNowall.id -> order.wallDesign = "Left wall not present"
                    viewBinding.rightNowall.id -> order.wallDesign = "Right wall not present"
                    viewBinding.allSideNowall.id -> order.wallDesign = "wall not present at both Left & Right side"

                }
                val gson = Gson()
                val intent = Intent(context, measureWithAR::class.java)
                intent.putExtra("order", gson.toJson(order))
                startActivity(context, intent, null)
            }else{
                Toast.makeText(context,"select all wall design first!",Toast.LENGTH_SHORT)
            }
        })
        viewBinding.btnManual.setOnClickListener(View.OnClickListener {
            val gson = Gson()
            val intent = Intent(context, ManualMeasure::class.java)
            intent.putExtra("order", gson.toJson(order))
            startActivity(context,intent,null)
        })
        viewBinding.btnQuestion.setOnClickListener(View.OnClickListener {
            OrderQuestionDialog.process(activity,context,layoutInflater)
        })
    }

}
