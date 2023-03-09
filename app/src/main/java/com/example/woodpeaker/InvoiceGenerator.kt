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
import com.itextpdf.layout.Style
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

class InvoiceGenerator : AppCompatActivity() {
    var order=Order()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        order.apply {
            title="L type white body kitchen"
            lengths.addAll(arrayListOf("2.5","4.1f","3f"))
            price="32000"
            totalPrice="40000"
            discountPercent="%8"
            address="Madarat, Baruipur\nSidheswari Kalitala\nnear Kalyani Construction Shop\npin-700144"
            finalPriceAftrDiscnt="35000"
            finalPriceAfterTax="42000"
            addons.add(Addon().apply {name="chimney";price="2000";quantity="2"})
            addons.add(Addon().apply {name="glass";price="1000";quantity="3"})
            addons.add(Addon().apply {name="rack";price="4000";quantity="1"})
        }

        val pdfpath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        var file=File(pdfpath,"invoice.pdf")
        var outputStream=FileOutputStream(file)
        var pdfWriter= PdfWriter(outputStream)
        var pdfDocument= PdfDocument(pdfWriter)
        pdfDocument.defaultPageSize= PageSize.A4
        var document= Document(pdfDocument)



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

        document.setTextAlignment(TextAlignment.CENTER)
        val headingTable=Table(floatArrayOf(300f,300f))
        headingTable.setBackgroundColor(DeviceRgb(13,131,221)).setFontColor(DeviceRgb(255,255,255))
        document.add(Paragraph("Invoice").setFontSize(20F))
//        headingTable.addCell(image)
        headingTable.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph("WoodPeaker")).setFontSize(20F)
            .setPadding(30f))
        headingTable.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph("WoodPeaker\nGSTIN: 69569369536936\n" +
                "ph:+91 9878543332\nwoodpeakercustomersupport@gmail.com")).setPadding(20f))
        document.add(headingTable)

        val itemHeading=Table(floatArrayOf(200f,200f,100f,100f)).setFontColor(DeviceRgb(13,131,221))
        itemHeading.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph("Description"))
            .setPadding(30f))
        itemHeading.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph("Qnit Cost"))
            .setPadding(30f))
        itemHeading.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph("Quantity"))
            .setPadding(30f))
        itemHeading.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph("Amount"))
            .setPadding(30f))

        val itemTable=Table(floatArrayOf(200f,200f,100f,100f)).setBold()
        var count=0

        itemTable.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph(order.title))
            .setPadding(15f))
        itemTable.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph(order.price))
            .setPadding(30f))
        itemTable.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph("1"))
            .setPadding(30f))
        itemTable.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph("Description"))
            .setPadding(30f))



        document.close()
    }
}