package com.proyecto.proyectoUnivdule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.github.barteksc.pdfviewer.PDFView
import com.proyecto.proyectoUnivdule.adapterDatos.AdapterPdfStream

class VisualizarPdfActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var pdfView: PDFView
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_pdf)

        url = intent.getStringExtra("url").toString()

        //Valor por dejecto para ver
        url = "https://www.fdi.ucm.es/profesor/luis/fp/fp.pdf"

        progressBar = findViewById(R.id.progressBar)
        pdfView = findViewById(R.id.pdf_viewer)

        AdapterPdfStream(pdfView, progressBar).execute(url)


    }


}