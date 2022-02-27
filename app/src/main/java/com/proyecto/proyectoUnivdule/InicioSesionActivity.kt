package com.proyecto.proyectoUnivdule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.room.Room
import androidx.room.RoomDatabase
import com.proyecto.proyectoUnivdule.administracionBBDD.UnivduleDB

class InicioSesionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bbdd: RoomDatabase
    private lateinit var etNombreUsuario: EditText
    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        bbdd = Room.databaseBuilder(this, UnivduleDB::class.java, "univdule").allowMainThreadQueries().fallbackToDestructiveMigration().build()

        etNombreUsuario = findViewById(R.id.etInicioSesionNombreUsuario)
        etPassword = findViewById(R.id.etInicioSesionPassword)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnInicioSesion -> iniciarSesion()
            R.id.btnRegistro -> registro()
        }
    }

    private fun registro() {
        TODO("Not yet implemented")
    }

    private fun iniciarSesion() {
        TODO("Not yet implemented")
    }
}