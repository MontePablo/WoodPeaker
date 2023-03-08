package com.example.woodpeaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)



        val path="D:\\DwnlData\\invoice.pdf"
        var pdfWriter= PdfWriter(path)
        var pdfDocument= PdfDocument(pdfWriter)
        var document= Document(pdfDocument)

        var col=280F
//        columnWidth=
        document.add(Paragraph("my name is shyamol"))
        document.close()
    }
}