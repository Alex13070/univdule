package com.proyecto.proyectoUnivdule

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.proyecto.proyectoUnivdule.administracionBBDD.UnivduleDB
import com.proyecto.proyectoUnivdule.modelo.Estudios
import java.lang.Exception

class EstudiosActivity : AppCompatActivity() {

    private lateinit var bbdd: UnivduleDB
    private lateinit var btnAdd: Button
    private var idUsuario: Int = 0

    private lateinit var etNombre: EditText
    private lateinit var etCurso: EditText
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudios)
        //idUsuario = intent.getIntExtra("usuario", 0)

        bbdd = Room.databaseBuilder(this, UnivduleDB::class.java, "univdule").allowMainThreadQueries().fallbackToDestructiveMigration().build()

        idUsuario = 2

        btnAdd = findViewById(R.id.btnEstudiosAdd)
        btnAdd.setOnClickListener {
            manejarDialog()
        }



    }

    private fun registrar() {
        System.err.println("osvfjnbf")
        var s = ""

        try {
            val nombre = etNombre.text.toString()
            val curso = Integer.parseInt(etCurso.text.toString())

            val estudios =  Estudios(0, nombre = nombre, curso = curso, idUsuario = idUsuario)
            bbdd.estudiosDAO().save(estudios = estudios)
            s = "Estudios guardados correctamente"

        }
        catch (e: Exception) {
            System.err.println(e.message)
            s = "Error al guardar los estudios"
        }

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun manejarDialog() {
        val builder = AlertDialog.Builder(this@EstudiosActivity)
        val view = layoutInflater.inflate(R.layout.dialogo_estudios, null)

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            view.getBackground().setAlpha(0);
        } else {
            view.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        }

        builder.setView(view)

        val dialog = builder.create()
        dialog.show()

        etNombre = view.findViewById<EditText>(R.id.etDialogoEstudiosNombre)
        etCurso = view.findViewById<EditText>(R.id.etDialogoEstudiosCurso)
        btnRegistrar = view.findViewById<Button>(R.id.btnDialogoEstudiosAdd)

        btnRegistrar.setOnClickListener {
            registrar()
        }
    }

}