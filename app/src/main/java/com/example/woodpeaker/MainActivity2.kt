package com.example.woodpeaker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.print.PrintAttributes.Margins
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import com.example.woodpeaker.models.Addon
import com.example.woodpeaker.models.Order
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity2 : AppCompatActivity() {
    var order=Order()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        order.apply {
            title="L type white body kitchen"
            lengths.addAll(arrayListOf("2.5","4.1f","3f"))
            price="32000"
            totalPrice="40000"
            finalPriceAftrDiscnt="35000"
            finalPriceAfterTax="42000"
            addons.add(Addon().apply {name="chimney";price="2000";quantity="2"})
            addons.add(Addon().apply {name="glass";price="1000";quantity="3"})
            addons.add(Addon().apply {name="rack";price="4000";quantity="1"})
        }

        val pdfpath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        var file=File(pdfpath,"invoice.pdf")
        var outputStream=FileOutputStream(file)
//        val path="D:\\DwnlData\\invoice.pdf"
        var pdfWriter= PdfWriter(outputStream)
        var pdfDocument= PdfDocument(pdfWriter)
        pdfDocument.defaultPageSize= PageSize.A4
        var document= Document(pdfDocument)

        document.setTextAlignment(TextAlignment.CENTER)
        var headingTable=Table(floatArrayOf(300f,300f))

//        val drawable=getDrawable(R.drawable.applogo_colouredbackround)
        //        val bitmap=drawable!!.toBitmap()
//        val ims=applicationContext.resources.assets.open("image/applogo.png")
//        val bitmap=BitmapFactory.decodeStream(ims)
//        val stream=ByteArrayOutputStream()
//        CoroutineScope(Dispatchers.Default).launch {
//            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
//        }
//        val bitmapData=stream.toByteArray()
//        var imageData= ImageDataFactory.create(bitmapData)
//        val image= Image(imageData)
//        image.scaleToFit(50f,50f)

        headingTable.setBackgroundColor(DeviceRgb(13,131,221)).setFontColor(DeviceRgb(255,255,255))
        document.add(Paragraph("Invoice").setFontSize(20F))
//        headingTable.addCell(image)
        headingTable.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph("WoodPeaker")).setFontSize(20F).setBorder(Border.NO_BORDER)
            .setPadding(30f))
        headingTable.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph("WoodPeaker\nGSTIN: 69569369536936\n" +
                "ph:+91 9878543332\nwoodpeakercustomersupport@gmail.com")).setPadding(20f))

//        document.add(Paragraph("my name is shyamol"))
//        document.add(table)
        document.add(headingTable)
        Log.d("TAG","pdf created")
        document.close()
    }
}