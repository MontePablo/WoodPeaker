package com.example.woodpeaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.woodpeaker.daos.StorageDao
import com.example.woodpeaker.models.Order
import com.rajat.pdfviewer.PdfViewerActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val c=InvoiceGenerator(Order())
        val invoiceUri=c.invoiceUri
        val orderId=c.orderId
        val invoiceId=c.invoiceId+".pdf"

        StorageDao.uploadProductInvoice(invoiceUri,invoiceId)?.addOnSuccessListener {
            Log.d("TAG","upload success")
            StorageDao.getInvoiceUrlOfProduct(invoiceId)!!.addOnSuccessListener {
                val invoiceLink=it.toString()
                Log.d("TAG","getting Url success ${invoiceLink}")
                startActivity(

                    // Use 'launchPdfFromPath' if you want to use assets file (enable "fromAssets" flag) / internal directory

                    PdfViewerActivity.launchPdfFromUrl(           //PdfViewerActivity.Companion.launchPdfFromUrl(..   :: incase of JAVA
                        this,
                        invoiceLink,                                // PDF URL in String format
                        "invoice ",                        // PDF Name/Title in String format
                        "",                  // If nothing specific, Put "" it will save to Downloads
                        true                    // This param is true by defualt.
                    )
                )
            }
        }?.addOnFailureListener { Log.d("TAG","uploadImage onFailure: ${it.localizedMessage}") }
    }
}