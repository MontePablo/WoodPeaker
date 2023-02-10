package com.example.woodpeaker

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.woodpeaker.databinding.ManualMeasureQuestionFragViewBinding
import com.example.woodpeaker.models.Product

object ManualMeasureQuestionDialog  {
    lateinit var activity: Activity
    lateinit var product: Product
    lateinit var context:Context
    lateinit var layoutInflater: LayoutInflater

    fun process(
        activity: Activity,
        context: Context,
        layoutInflater: LayoutInflater) {
        Log.d("TAG","main func start")
        Log.d("TAG","product: ${product!!.title}")
        Log.d("TAG","context:$context")
        Log.d("TAG","activity: $activity")

        this.activity=activity
        this.product= product!!
        this.context=context
        this.layoutInflater=layoutInflater
        var dialogBuilder= AlertDialog.Builder(context)
        var viewBinding= ManualMeasureQuestionFragViewBinding.inflate(layoutInflater)
        dialogBuilder.setView(viewBinding.root)
        val dialog=dialogBuilder.create()
        dialog.show()
    }

}
