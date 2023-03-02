package com.example.woodpeaker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.woodpeaker.databinding.WallQuestionFragViewBinding
import com.example.woodpeaker.models.Product
import com.google.gson.Gson

object WallQuestionDialog  {
    lateinit var activity: Activity
    lateinit var context:Context
    lateinit var layoutInflater: LayoutInflater

    fun process(
        activity: Activity,
        context: Context,
        layoutInflater: LayoutInflater) {

        this.activity=activity
        this.context=context
        this.layoutInflater=layoutInflater
        var dialogBuilder= AlertDialog.Builder(context)
        var viewBinding= WallQuestionFragViewBinding.inflate(layoutInflater)
        dialogBuilder.setView(viewBinding.root)
        val dialog=dialogBuilder.create()
        dialog.show()
    }

}
