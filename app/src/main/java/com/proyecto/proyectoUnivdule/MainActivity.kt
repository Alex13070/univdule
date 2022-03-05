package com.proyecto.proyectoUnivdule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.RoomDatabase
import com.proyecto.proyectoUnivdule.administracionBBDD.UnivduleDB
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bbdd: UnivduleDB
    private var idUsuario = -1

    private lateinit var btnCalendario: View
    private lateinit var btnEstudios: View
    private lateinit var btnTareas: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        idUsuario = intent.getIntExtra("id_usuario", -1)

        btnCalendario = findViewById(R.id.btnMainCalendario)
        btnEstudios = findViewById(R.id.btnMainEstudios)
        btnTareas = findViewById(R.id.btnMainTarea)

        btnEstudios.setOnClickListener(this)
        btnCalendario.setOnClickListener(this)
        btnTareas.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnMainCalendario -> empezarCalendario()
            R.id.btnMainEstudios -> empezarEstudios()
            R.id.btnMainTarea -> empezarTareas()
        }
    }

    private fun empezarTareas() {
        var intent = Intent(this, TareaActivity::class.java)
        intent.putExtra("id_usuario", idUsuario)
        startActivity(intent)
    }

    private fun empezarEstudios() {
        var intent = Intent(this, EstudiosActivity::class.java)
        intent.putExtra("id_usuario", idUsuario)
        startActivity(intent)
    }

    private fun empezarCalendario() {
        var intent = Intent(this, CalendarioActivity::class.java)
        intent.putExtra("id_usuario", idUsuario)
        startActivity(intent)
    }
}