package com.proyecto.proyectoUnivdule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import androidx.room.RoomDatabase
import com.proyecto.proyectoUnivdule.administracionBBDD.UnivduleDB
import com.proyecto.proyectoUnivdule.modelo.Usuario
import java.util.*

class InicioSesionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bbdd: UnivduleDB
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
        val intent = Intent(this,  RegistroActivity::class.java)
        startActivity(intent)
    }

    private fun iniciarSesion() {
        var nombreUsuario = etNombreUsuario.text.toString()
        var password = etPassword.text.toString()

        var usu: Optional<Usuario> = Optional.of(bbdd.usuarioDAO().findByUserName(nombreUsuario))

        if (usu.isPresent) {
            var usuario = usu.get()

        }
        else
            Toast.makeText(this, "El usuario introducido no existe", Toast.LENGTH_SHORT).show()

    }
}