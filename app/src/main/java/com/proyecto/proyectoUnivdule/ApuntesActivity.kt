package com.proyecto.proyectoUnivdule

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.proyecto.proyectoUnivdule.adapterDatos.AdapterApuntes
import com.proyecto.proyectoUnivdule.adapterDatos.AdapterAsignaturas
import com.proyecto.proyectoUnivdule.adapterDatos.AdapterEstudios
import com.proyecto.proyectoUnivdule.administracionBBDD.UnivduleDB
import com.proyecto.proyectoUnivdule.modelo.Apuntes
import com.proyecto.proyectoUnivdule.modelo.Asignatura
import com.proyecto.proyectoUnivdule.modelo.Estudios
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ApuntesActivity : AppCompatActivity() {

    //Base de datos
    private lateinit var bbdd: UnivduleDB
    private var idAsignatura: Int = 0

    //Dialog
    private lateinit var etNombre: EditText
    private lateinit var etDireccion: EditText
    private lateinit var btnRegistrar: Button

    //RecycleViewer
    private lateinit var listaApuntes: ArrayList<Apuntes>
    private lateinit var listaTemporal: ArrayList<Apuntes>
    private lateinit var rvApuntes: RecyclerView

    //Botones
    private lateinit var btnAdd: Button
    private lateinit var btnBorrar: Button
    private lateinit var btnTareas: Button

    //Borrar datos o no
    private var borrar = false

    //Constructor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apuntes)

        //Base de datos
        bbdd = Room.databaseBuilder(this, UnivduleDB::class.java, "univdule").allowMainThreadQueries().fallbackToDestructiveMigration().build()
        idAsignatura = intent.getIntExtra("id_asignatura", -1)

        //Botones
        btnAdd = findViewById(R.id.btnApuntesAdd)
        btnAdd.setOnClickListener {
            manejarDialog()
        }
        btnBorrar = findViewById(R.id.btnApuntesDelete)
        btnBorrar.setOnClickListener {
            accionBorrar()
        }

        btnTareas = findViewById(R.id.btnApuntesTareas)
        btnTareas.setOnClickListener {
            //Hacer cosas
        }


        //RecycleViewer
        listaApuntes = bbdd.apuntesDAO().findByAsignatura(id_asignatura = idAsignatura) as ArrayList<Apuntes>
        listaTemporal = ArrayList(listaApuntes)

        rvApuntes = findViewById<RecyclerView>(R.id.rvApuntes)

        rellenarRV()

    }

    //Activa el boolean que hace que borres datos
    private fun accionBorrar() {
        borrar = !borrar

        var buttonDrawable = btnBorrar.background
        buttonDrawable = DrawableCompat.wrap(buttonDrawable)

        if (borrar)
            DrawableCompat.setTint(buttonDrawable, Color.rgb(187,134,252))
        else
            DrawableCompat.setTint(buttonDrawable, Color.rgb(98,0,238))

    }

    //Barra de busqueda
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                listaTemporal.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())

                if (searchText.isNotEmpty()) {

                    listaApuntes.forEach {

                        if (it.nombre.toLowerCase().contains(searchText)){
                            listaTemporal.add(it)
                        }

                    }

                    rvApuntes.adapter?.notifyDataSetChanged()

                }
                else {
                    listaTemporal.clear()
                    listaTemporal.addAll(listaApuntes)
                    rvApuntes.adapter!!.notifyDataSetChanged()
                }


                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    //Rellenar el RecycleViewer
    private fun rellenarRV() {
        rvApuntes.layoutManager = LinearLayoutManager(this)
        var adapter = AdapterApuntes(listaTemporal)

        adapter.setOnItemClickListener(object : AdapterApuntes.OnItemClickListener{
            override fun onItemClick(position: Int) {
                accionRV(position)
            }
        })

        rvApuntes.adapter = adapter
    }

    //Accion al pulsar un campo del RecycleViewer
    private fun accionRV(position: Int) {
        var apuntes = listaApuntes[position]

        if (!borrar)
            cambiarActivity(apuntes)
        else
            borrarEstudios(apuntes)
    }

    //Borrar estudios
    private fun borrarEstudios(apuntes: Apuntes) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Alerta")
            .setMessage("Se borrarán los estudios seleccionados. \n${apuntes.toString()}\n ¿Quieres continuar?")
            .setPositiveButton("Aceptar", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var str = ""
                    try {
                        bbdd.apuntesDAO().delete(apuntes = apuntes)
                        str = "Estudios borrados correctamente"
                        listaTemporal.remove(apuntes)
                        listaApuntes.remove(apuntes)
                        rvApuntes.adapter!!.notifyDataSetChanged()
                    }
                    catch (e: Exception) {
                        str = "Error al guardar los estudios"
                    }

                    Toast.makeText(this@ApuntesActivity, str, Toast.LENGTH_SHORT).show()
                }
            })
            .setNegativeButton("Cancelar", null)
            .show()
    }

    //Cambiar de actividad
    private fun cambiarActivity(apuntes: Apuntes) {
        Toast.makeText(this, "${apuntes.toString()}", Toast.LENGTH_SHORT).show()
    }

    //Regjstrar unos estudios
    private fun registrar() {
        var s = ""

        try {
            val nombre = etNombre.text.toString()
            val direccion = etDireccion.text.toString()

            if (etNombre.text.isNotEmpty()) {

                val apuntes =  Apuntes(idApuntes = 0, nombre = nombre, direccion = direccion, idAsignatura = idAsignatura)
                bbdd.apuntesDAO().save(apuntes = apuntes)



                listaApuntes = bbdd.apuntesDAO().findByAsignatura(idAsignatura) as ArrayList<Apuntes>
                listaTemporal.clear()
                listaTemporal.addAll(listaApuntes)
                rvApuntes.adapter!!.notifyDataSetChanged()

                s = "Estudios guardados correctamente"

            }
            else
                s = "Faltan campos por rellenar"


        }
        catch (e: Exception) {
            System.err.println(e.message)
            s = "Error al guardar los estudios"
        }

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    //Manejo del dialog
    private fun manejarDialog() {
        val builder = AlertDialog.Builder(this@ApuntesActivity)
        val view = layoutInflater.inflate(R.layout.dialogo_apuntes, null)

        builder.setView(view)

        val dialog = builder.create()
        dialog.show()

        etNombre = view.findViewById<EditText>(R.id.etDialogoApuntesNombre)
        etDireccion = view.findViewById<EditText>(R.id.etDialogoApuntesDireccion)
        btnRegistrar = view.findViewById<Button>(R.id.btnDialogoApuntesAdd)

        btnRegistrar.setOnClickListener {
            registrar()
            dialog.hide()
        }
    }



}