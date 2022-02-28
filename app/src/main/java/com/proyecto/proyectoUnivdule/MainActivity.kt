package com.proyecto.proyectoUnivdule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.RoomDatabase
import com.proyecto.proyectoUnivdule.administracionBBDD.UnivduleDB
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bbdd: UnivduleDB
    private var idUsuario = -1
    private lateinit var btnCalendario: Button
    private lateinit var btnEstudios: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        idUsuario = intent.getIntExtra("id_usuario", -1)

        btnCalendario = findViewById(R.id.btnMainCalendario)
        btnEstudios = findViewById(R.id.btnMainEstudios)

        btnEstudios.setOnClickListener(this)
        btnCalendario.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnMainCalendario -> empezarCalendario()
            R.id.btnMainEstudios -> empezarEstudios()
        }
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