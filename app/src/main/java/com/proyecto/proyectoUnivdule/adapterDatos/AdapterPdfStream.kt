package com.proyecto.proyectoUnivdule.adapterDatos

import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@SuppressWarnings("deprecated")
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
        pdfView.fromStream(result)
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
            .password(null)
            .scrollHandle(null)
            .enableAntialiasing(true) // improve rendering a little bit on low-res screens
            // spacing between pages in dp. To define spacing color, set view background
            .spacing(2)
            .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
            .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
            .pageSnap(false) // snap pages to screen boundaries
            .pageFling(false) // make a fling change only a single page like ViewPager
            .nightMode(false) // toggle night mode
            .load();
        progressBar.visibility = View.GONE
    }
}

















