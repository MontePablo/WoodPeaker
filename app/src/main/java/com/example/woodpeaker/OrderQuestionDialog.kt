package com.example.woodpeaker

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.widget.MediaController
import androidx.appcompat.app.AlertDialog
import com.example.woodpeaker.databinding.OrderQuestionFragViewBinding

object OrderQuestionDialog  {
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
        var viewBinding= OrderQuestionFragViewBinding.inflate(layoutInflater)
        dialogBuilder.setView(viewBinding.root)
        val dialog=dialogBuilder.create()
        dialog.show()


//        val videoPath = "android.resource://" + context.packageName + "/" + com.example.woodpeaker.R.raw.video
//        val uri = Uri.parse(videoPath)
//        viewBinding.videoView.setVideoURI(uri)
//
//        val mediaController = MediaController(context)
//        viewBinding.videoView.setMediaController(mediaController)
//        mediaController.setAnchorView(viewBinding.videoView)
    }

}
