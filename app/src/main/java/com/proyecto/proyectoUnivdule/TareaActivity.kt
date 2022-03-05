package com.proyecto.proyectoUnivdule

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.proyecto.proyectoUnivdule.adapterDatos.AdapterApuntes
import com.proyecto.proyectoUnivdule.adapterDatos.AdapterTareas
import com.proyecto.proyectoUnivdule.administracionBBDD.UnivduleDB
import com.proyecto.proyectoUnivdule.modelo.Tarea
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class TareaActivity : AppCompatActivity() {

    //Base de datos
    private lateinit var bbdd: UnivduleDB
    private var idUsuario: Int = 0

    //RecycleViewer
    private lateinit var listaTarea: ArrayList<Tarea>
    private lateinit var listaTemporal: ArrayList<Tarea>
    private lateinit var rvTareas: RecyclerView

    //Botones
    private lateinit var btnBorrar: Button

    //Borrar datos o no
    private var borrar = false


    //Constructor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea)

        //Base de datos
        bbdd = Room.databaseBuilder(this, UnivduleDB::class.java, "univdule").allowMainThreadQueries().fallbackToDestructiveMigration().build()
        idUsuario = intent.getIntExtra("id_usuario", -1)

        //Botones
        btnBorrar = findViewById(R.id.btnTareaDelete)
        btnBorrar.setOnClickListener {
            accionBorrar()
        }

        //RecycleViewer
        listaTarea = bbdd.tareaDAO().findByUsuario(id_usuario = idUsuario) as ArrayList<Tarea>
        listaTemporal = ArrayList(listaTarea)

        rvTareas = findViewById<RecyclerView>(R.id.rvTarea)
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
            DrawableCompat.setTint(buttonDrawable, Color.rgb(33,0,255))

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

                    listaTarea.forEach {

                        if (it.nombre.toLowerCase().contains(searchText)){
                            listaTemporal.add(it)
                        }
                    }
                    rvTareas.adapter?.notifyDataSetChanged()
                }
                else {
                    listaTemporal.clear()
                    listaTemporal.addAll(listaTarea)
                    rvTareas.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    //Rellenar el RecycleViewer
    private fun rellenarRV() {
        rvTareas.layoutManager = LinearLayoutManager(this)
        var adapter = AdapterTareas(listaTemporal, bbdd)

        adapter.setOnItemClickListener(object : AdapterTareas.OnItemClickListener{
            override fun onItemClick(position: Int) {
                accionRV(position)
            }
        })

        rvTareas.adapter = adapter
    }

    //Accion al pulsar un campo del RecycleViewer
    private fun accionRV(position: Int) {
        borrar = !borrar

        var estudios = listaTarea[position]

        if (!borrar)
            borrarEstudios(estudios)

    }

    //Borrar estudios
    private fun borrarEstudios(tarea: Tarea) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Alerta")
            .setMessage("Se borrará la tarea seleccionada. \n${tarea.toString()}\n ¿Quieres continuar?")
            .setPositiveButton("Aceptar", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var str = ""
                    try {
                        bbdd.tareaDAO().delete(tarea = tarea)
                        str = "Tarea borrada correctamente"
                        listaTemporal.remove(tarea)
                        listaTarea.remove(tarea)
                        rvTareas.adapter!!.notifyDataSetChanged()
                    }
                    catch (e: Exception) {
                        str = "Error al guardar la tarea"
                    }

                    Toast.makeText(this@TareaActivity, str, Toast.LENGTH_SHORT).show()
                }
            })
            .setNegativeButton("Cancelar", null)
            .show()
    }

}
