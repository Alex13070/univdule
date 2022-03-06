package com.proyecto.proyectoUnivdule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.github.barteksc.pdfviewer.PDFView
import com.proyecto.proyectoUnivdule.adapterDatos.AdapterPdfStream
import java.lang.Exception

class VisualizarPdfActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var pdfView: PDFView
    private lateinit var url: String
    private var id = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_pdf)

        url = intent.getStringExtra("url").toString()
        id = intent.getIntExtra("id_asignatura", -1)
        //Valor por dejecto para ver
        //url = "https://www.fdi.ucm.es/profesor/luis/fp/fp.pdf"

        progressBar = findViewById(R.id.progressBar)
        pdfView = findViewById(R.id.pdf_viewer)

        try {
            AdapterPdfStream(pdfView, progressBar).execute(url)
        }
        catch (e: Exception){
            val intent = Intent(this, ApuntesActivity::class.java)
            intent.putExtra("id_asignatura", id)
            startActivity(intent)
        }
    }
}