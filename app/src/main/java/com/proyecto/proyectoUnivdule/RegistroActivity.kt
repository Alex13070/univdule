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

    //Base de datos
    private lateinit var bbdd: UnivduleDB

    //Edit text del formulario
    private lateinit var etNombreUsuario: EditText
    private lateinit var etNombre: EditText
    private lateinit var etClave: EditText
    private lateinit var etClave2: EditText

    //Boton de registro
    private lateinit var btnRegistrarUsuario: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        bbdd = Room.databaseBuilder(this, UnivduleDB::class.java, "univdule").allowMainThreadQueries().fallbackToDestructiveMigration().build()
        etNombreUsuario = findViewById(R.id.etRegistroNombreUsuario)
        etNombre = findViewById(R.id.etRegistroNombre)
        etClave = findViewById(R.id.etRegistroClave)
        etClave2 = findViewById(R.id.etRegistroClaveRepetir)

        btnRegistrarUsuario = findViewById(R.id.btnRegistroRegistrar)
        btnRegistrarUsuario.setOnClickListener(this)
    }

    //On click de los botones
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnRegistroRegistrar -> comprobarDatosRegistro()
        }
    }

    //Comprobar los datos del registro
    private fun comprobarDatosRegistro() {


        if (etClave.text.toString().equals(etClave2.text.toString())){
            var nombreUsuario = etNombreUsuario.text.toString()
            var usu: Optional<Usuario> = Optional.ofNullable(bbdd.usuarioDAO().findByUserName(nombreUsuario))

            if (!usu.isPresent){
                registrarUsuario()
            }
            else{
                Toast.makeText(this, "El nombre de usuario introducido ya existe.", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            }
        }
        else {
            Toast.makeText(this, "Las claves no coinciden.", Toast.LENGTH_SHORT).show()
            limpiarClaves()
        }

        limpiarCampos()
    }

    //Registro de usuarios
    private fun registrarUsuario(){
        var nombreUsuario = etNombreUsuario.text.toString()
        var nombre = etNombre.text.toString()
        var contrasenya = etClave.text.toString()

        var usuario = Usuario(0, nombreUsuario, nombre, contrasenya)

        lateinit var msg: String

        try {
            bbdd.usuarioDAO().save(usuario)
            msg = "Registro correcto"
        }
        catch (e: Exception){
            msg = "Error al guardar el usuario"
        }

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    //limpiar toodos los campos
    private fun limpiarCampos(){
        etNombreUsuario.text.clear()
        etNombre.text.clear()
        limpiarClaves()
    }

    //limpiar solo las claves
    private fun limpiarClaves(){
        etClave.text.clear()
        etClave2.text.clear()
    }


}