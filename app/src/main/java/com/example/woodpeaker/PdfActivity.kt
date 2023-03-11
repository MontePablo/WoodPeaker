package com.example.woodpeaker

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.woodpeaker.databinding.ActivityPdfBinding


class PdfActivity : AppCompatActivity() {
    lateinit var binding: ActivityPdfBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Toast.makeText(this,"downloading.. please wait",Toast.LENGTH_SHORT)
        val pdfLink=intent.getStringExtra("pdfLink")
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(pdfLink)
        val request: DownloadManager.Request = DownloadManager.Request(uri)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "invoice.pdf"
            )
            .setTitle("invoice.pdf")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        val reference: Long = manager.enqueue(request)
        Handler().postDelayed({
            val uri = manager.getUriForDownloadedFile(reference)
            binding.pdfView.fromUri(uri).load()
            Toast.makeText(this,"Saved to Downloads!",Toast.LENGTH_SHORT)
        }, 1500)



    }
}