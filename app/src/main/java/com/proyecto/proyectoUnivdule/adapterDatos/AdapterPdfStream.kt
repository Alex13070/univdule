package com.proyecto.proyectoUnivdule.adapterDatos

import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import com.github.barteksc.pdfviewer.PDFView
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class AdapterPdfStream(var pdfView: PDFView, var progressBar: ProgressBar): AsyncTask<String, Void, InputStream>() {
    override fun doInBackground(vararg params: String?): InputStream? {
        var inputStream: InputStream? = null
        try {
            var url: URL = URL(params[0])
            var urlConection: HttpURLConnection = url.openConnection() as HttpURLConnection

            if (urlConection.responseCode == 200){
                inputStream = BufferedInputStream(urlConection.inputStream)
            }
        }
        catch (e: IOException) {
            System.err.println("Error al leer el pdf")
        }

        return inputStream
    }

    override fun onPostExecute(result: InputStream?) {
        pdfView.fromStream(result).load()
        progressBar.visibility = View.GONE
    }
}

















