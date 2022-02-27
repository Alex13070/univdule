package com.proyecto.proyectoUnivdule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import androidx.room.RoomDatabase
import com.proyecto.proyectoUnivdule.administracionBBDD.UnivduleDB
import com.proyecto.proyectoUnivdule.modelo.Usuario
import java.lang.Exception
import java.util.*

class RegistroActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bbdd: UnivduleDB
    private lateinit var etNombreUsuario: EditText
    private lateinit var etNombre: EditText
    private lateinit var etContrasenya: EditText

    private lateinit var btnRegistrarUsuario: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        bbdd = Room.databaseBuilder(this, UnivduleDB::class.java, "univdule").allowMainThreadQueries().fallbackToDestructiveMigration().build()
        etNombreUsuario = findViewById(R.id.etRegistroNombreUsuario)
        etNombre = findViewById(R.id.etRegistroNombre)
        etContrasenya = findViewById(R.id.etRegistroClave)

        btnRegistrarUsuario = findViewById(R.id.btnRegistroRegistrar)
        btnRegistrarUsuario.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnRegistroRegistrar -> comprobarDatosRegistro()
        }
    }
    private fun comprobarDatosRegistro() {
        var nombreUsuario = etNombreUsuario.text.toString()
        var usu: Optional<Usuario> = Optional.ofNullable(bbdd.usuarioDAO().findByUserName(nombreUsuario))

        if (!usu.isPresent)
            registrarUsuario()
        else
            Toast.makeText(this, "El usuario introducido no existe.", Toast.LENGTH_SHORT).show()

        limpiarCampos()
    }

    private fun registrarUsuario(){
        var nombreUsuario = etNombreUsuario.text.toString()
        var nombre = etNombre.text.toString()
        var contrasenya = etContrasenya.text.toString()
        var usuario = Usuario(0, nombreUsuario, nombre, contrasenya)

        try {
            bbdd.usuarioDAO().save(usuario)
        }
        catch (e: Exception){
            Toast.makeText(this, "Error al guardar el usuario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos(){
        etNombreUsuario.text.clear()
        etNombre.text.clear()
        etContrasenya.text.clear()
    }


}