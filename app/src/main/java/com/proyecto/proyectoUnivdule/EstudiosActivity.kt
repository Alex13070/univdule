package com.proyecto.proyectoUnivdule

import android.content.DialogInterface
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
import com.proyecto.proyectoUnivdule.adapterDatos.AdapterEstudios
import com.proyecto.proyectoUnivdule.administracionBBDD.UnivduleDB
import com.proyecto.proyectoUnivdule.modelo.Estudios
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class EstudiosActivity : AppCompatActivity() {

    //Base de datos
    private lateinit var bbdd: UnivduleDB
    private var idUsuario: Int = 0

    //Dialog
    private lateinit var etNombre: EditText
    private lateinit var etCurso: EditText
    private lateinit var btnRegistrar: Button

    //RecycleViewer
    private lateinit var listaEstudios: ArrayList<Estudios>
    private lateinit var listaTemporal: ArrayList<Estudios>
    private lateinit var rvEstudios: RecyclerView

    //Botones
    private lateinit var btnAdd: Button
    private lateinit var btnBorrar: Button

    //Borrar datos o no
    private var borrar = false

    //Constructor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudios)

        //Base de datos
        bbdd = Room.databaseBuilder(this, UnivduleDB::class.java, "univdule").allowMainThreadQueries().fallbackToDestructiveMigration().build()
        idUsuario = intent.getIntExtra("id_usuario", -1)

        //Botones
        btnAdd = findViewById(R.id.btnEstudiosAdd)
        btnAdd.setOnClickListener {
            manejarDialog()
        }
        btnBorrar = findViewById(R.id.btnEstudiosDelete)
        btnBorrar.setOnClickListener {
            accionBorrar()
        }


        //RecycleViewer
        listaEstudios = bbdd.estudiosDAO().findByUsuario(idUsuario) as ArrayList<Estudios>
        listaTemporal = ArrayList(listaEstudios)

        rvEstudios = findViewById<RecyclerView>(R.id.rvEstudios)
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

                    listaEstudios.forEach {

                        if (it.nombre.toLowerCase().contains(searchText)){
                            listaTemporal.add(it)
                        }

                    }

                    rvEstudios.adapter?.notifyDataSetChanged()

                }
                else {
                    listaTemporal.clear()
                    listaTemporal.addAll(listaEstudios)
                    rvEstudios.adapter!!.notifyDataSetChanged()
                }


                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    //Rellenar el RecycleViewer
    private fun rellenarRV() {
        rvEstudios.layoutManager = LinearLayoutManager(this)
        var adapter = AdapterEstudios(listaTemporal)

        adapter.setOnItemClickListener(object : AdapterEstudios.OnItemClickListener{
            override fun onItemClick(position: Int) {
                accionRV(position)
            }
        })

        rvEstudios.adapter = adapter
    }

    //Accion al pulsar un campo del RecycleViewer
    private fun accionRV(position: Int) {
        var estudios = listaEstudios[position]

        if (!borrar)
            cambiarActivity(estudios)
        else
            borrarEstudios(estudios)
    }

    //Borrar estudios
    private fun borrarEstudios(estudios: Estudios) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Alerta")
            .setMessage("Se borrarán los estudios seleccionados. \n${estudios.toString()}\n ¿Quieres continuar?")
            .setPositiveButton("Aceptar", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var str = ""
                    try {
                        bbdd.estudiosDAO().delete(estudios = estudios)
                        str = "Estudios borrados correctamente"
                        listaTemporal.remove(estudios)
                        listaEstudios.remove(estudios)
                        rvEstudios.adapter!!.notifyDataSetChanged()
                    }
                    catch (e: Exception) {
                        str = "Error al guardar los estudios"
                    }

                    Toast.makeText(this@EstudiosActivity, str, Toast.LENGTH_SHORT).show()
                }
            })
            .setNegativeButton("Cancelar", null)
            .show()
    }

    //Cambiar de actividad
    private fun cambiarActivity(estudios: Estudios) {
        Toast.makeText(this, "Cambiar de actividad", Toast.LENGTH_SHORT).show()
    }

    //Regjstrar unos estudios
    private fun registrar() {
        var s = ""

        try {
            val nombre = etNombre.text.toString()
            val curso = Integer.parseInt(etCurso.text.toString())

            if (etNombre.text.isNotEmpty() && etCurso.text.isNotEmpty()) {

                val estudios =  Estudios(0, nombre = nombre, curso = curso, idUsuario = idUsuario)
                bbdd.estudiosDAO().save(estudios = estudios)

                listaEstudios.add(estudios)
                listaTemporal.add(estudios)
                rvEstudios.adapter!!.notifyDataSetChanged()

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
        val builder = AlertDialog.Builder(this@EstudiosActivity)
        val view = layoutInflater.inflate(R.layout.dialogo_estudios, null)

        builder.setView(view)

        val dialog = builder.create()
        dialog.show()

        etNombre = view.findViewById<EditText>(R.id.etDialogoEstudiosNombre)
        etCurso = view.findViewById<EditText>(R.id.etDialogoEstudiosCurso)
        btnRegistrar = view.findViewById<Button>(R.id.btnDialogoEstudiosAdd)

        btnRegistrar.setOnClickListener {
            registrar()
            dialog.hide()
        }
    }

}