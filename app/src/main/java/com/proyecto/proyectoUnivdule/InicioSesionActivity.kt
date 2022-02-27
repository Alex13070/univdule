package com.proyecto.proyectoUnivdule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
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

    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        bbdd = Room.databaseBuilder(this, UnivduleDB::class.java, "univdule").allowMainThreadQueries().fallbackToDestructiveMigration().build()

        etNombreUsuario = findViewById(R.id.etInicioSesionNombreUsuario)

        etPassword = findViewById(R.id.etInicioSesionPassword)

        btnIniciarSesion = findViewById(R.id.btnInicioSesionInicioSesion)
        btnIniciarSesion.setOnClickListener(this)

        btnRegistrar = findViewById(R.id.btnInicioSesionRegistro)
        btnRegistrar.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnInicioSesionInicioSesion -> comprobarDatos()
            R.id.btnInicioSesionRegistro -> registro()
        }
    }

    private fun registro() {
        val intent = Intent(this,  RegistroActivity::class.java)
        startActivity(intent)
    }

    private fun comprobarDatos() {
        var nombreUsuario = etNombreUsuario.text.toString()
        var password = etPassword.text.toString()

        var usu: Optional<Usuario> = Optional.ofNullable(bbdd.usuarioDAO().findByUserName(nombreUsuario))

        if (usu.isPresent)
            if (usu.get().clave.equals(password))
                iniciarSesion()
            else
                Toast.makeText(this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "El usuario introducido no existe.", Toast.LENGTH_SHORT).show()

    }

    private fun iniciarSesion() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}