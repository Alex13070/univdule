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
import com.proyecto.proyectoUnivdule.adapterDatos.AdapterAsignaturas
import com.proyecto.proyectoUnivdule.adapterDatos.AdapterEstudios
import com.proyecto.proyectoUnivdule.administracionBBDD.UnivduleDB
import com.proyecto.proyectoUnivdule.modelo.Asignatura
import com.proyecto.proyectoUnivdule.modelo.Estudios
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class AsignaturasActivity : AppCompatActivity() {

    //Base de datos
    private lateinit var bbdd: UnivduleDB
    private var idEstudios: Int = 0

    //Dialog
    private lateinit var etNombre: EditText
    private lateinit var btnRegistrar: Button

    //RecycleViewer
    private lateinit var listaAsignaturas: ArrayList<Asignatura>
    private lateinit var listaTemporal: ArrayList<Asignatura>
    private lateinit var rvAsignatura: RecyclerView

    //Botones
    private lateinit var btnAdd: Button
    private lateinit var btnBorrar: Button

    //Borrar datos o no
    private var borrar = false

    //Constructor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignaturas)

        //Base de datos
        bbdd = Room.databaseBuilder(this, UnivduleDB::class.java, "univdule").allowMainThreadQueries().fallbackToDestructiveMigration().build()
        idEstudios = intent.getIntExtra("id_estudios", -1)

        //Botones
        btnAdd = findViewById(R.id.btnAsignaturasAdd)
        btnAdd.setOnClickListener {
            manejarDialog()
        }
        btnBorrar = findViewById(R.id.btnAsignaturasDelete)
        btnBorrar.setOnClickListener {
            accionBorrar()
        }


        //RecycleViewer
        listaAsignaturas = bbdd.asignaturaDAO().findByEstudios(idEstudios) as ArrayList<Asignatura>
        listaTemporal = ArrayList(listaAsignaturas)

        rvAsignatura = findViewById<RecyclerView>(R.id.rvAsignaturas)

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

                    listaAsignaturas.forEach {

                        if (it.nombre.toLowerCase().contains(searchText)){
                            listaTemporal.add(it)
                        }

                    }

                    rvAsignatura.adapter?.notifyDataSetChanged()

                }
                else {
                    listaTemporal.clear()
                    listaTemporal.addAll(listaAsignaturas)
                    rvAsignatura.adapter!!.notifyDataSetChanged()
                }


                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    //Rellenar el RecycleViewer
    private fun rellenarRV() {
        rvAsignatura.layoutManager = LinearLayoutManager(this)
        var adapter = AdapterAsignaturas(listaTemporal)

        adapter.setOnItemClickListener(object : AdapterAsignaturas.OnItemClickListener{
            override fun onItemClick(position: Int) {
                accionRV(position)
            }
        })

        rvAsignatura.adapter = adapter
    }

    //Accion al pulsar un campo del RecycleViewer
    private fun accionRV(position: Int) {
        var asignatura = listaAsignaturas[position]

        if (!borrar)
            cambiarActivity(asignatura)
        else
            borrarEstudios(asignatura)
    }

    //Borrar estudios
    private fun borrarEstudios(asignatura: Asignatura) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Alerta")
            .setMessage("Se borrarán los estudios seleccionados. \n${asignatura.toString()}\n ¿Quieres continuar?")
            .setPositiveButton("Aceptar", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var str = ""
                    try {
                        bbdd.asignaturaDAO().delete(asignatura = asignatura)
                        str = "Estudios borrados correctamente"
                        listaTemporal.remove(asignatura)
                        listaAsignaturas.remove(asignatura)
                        rvAsignatura.adapter!!.notifyDataSetChanged()
                    }
                    catch (e: Exception) {
                        str = "Error al guardar los estudios"
                    }

                    Toast.makeText(this@AsignaturasActivity, str, Toast.LENGTH_SHORT).show()
                }
            })
            .setNegativeButton("Cancelar", null)
            .show()
    }

    //Cambiar de actividad
    private fun cambiarActivity(asignatura: Asignatura) {
        val intent = Intent(this, ApuntesActivity::class.java)
        intent.putExtra("id_asignatura", asignatura.idAsignatura)
        try {
            startActivity(intent)
        }
        catch (e: Exception) {
            Toast.makeText(this, "Error al cambiar de actividad", Toast.LENGTH_SHORT).show()
        }
    }

    //Regjstrar unos estudios
    private fun registrar() {
        var s = ""

        try {
            val nombre = etNombre.text.toString()

            if (etNombre.text.isNotEmpty()) {

                val asignatura =  Asignatura(0, nombre = nombre, idEstudios = idEstudios)
                bbdd.asignaturaDAO().save(asignatura = asignatura)

                listaAsignaturas.add(asignatura)
                listaTemporal.add(asignatura)
                rvAsignatura.adapter!!.notifyDataSetChanged()

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
        val builder = AlertDialog.Builder(this@AsignaturasActivity)
        val view = layoutInflater.inflate(R.layout.dialogo_asignaturas, null)

        builder.setView(view)

        val dialog = builder.create()
        dialog.show()

        etNombre = view.findViewById<EditText>(R.id.etDialogoAsignaturasNombre)
        btnRegistrar = view.findViewById<Button>(R.id.btnDialogoAsignaturasAdd)

        btnRegistrar.setOnClickListener {
            registrar()
            dialog.hide()
        }
    }

}