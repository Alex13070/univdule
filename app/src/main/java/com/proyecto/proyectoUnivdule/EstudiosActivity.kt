package com.proyecto.proyectoUnivdule

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.proyecto.proyectoUnivdule.adapterDatos.AdapterEstudios
import com.proyecto.proyectoUnivdule.administracionBBDD.UnivduleDB
import com.proyecto.proyectoUnivdule.modelo.Estudios
import java.lang.Exception
import java.util.*
import java.util.function.Predicate
import kotlin.collections.ArrayList

class EstudiosActivity : AppCompatActivity() {

    private lateinit var bbdd: UnivduleDB
    private lateinit var btnAdd: Button
    private var idUsuario: Int = 0

    private lateinit var etNombre: EditText
    private lateinit var etCurso: EditText
    private lateinit var btnRegistrar: Button

    //RecycleViewer
    private lateinit var listaEstudios: ArrayList<Estudios>
    private lateinit var listaTemporal: ArrayList<Estudios>
    private lateinit var rvEstudios: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudios)
        idUsuario = intent.getIntExtra("id_usuario", -1)

        bbdd = Room.databaseBuilder(this, UnivduleDB::class.java, "univdule").allowMainThreadQueries().fallbackToDestructiveMigration().build()


        btnAdd = findViewById(R.id.btnEstudiosAdd)
        btnAdd.setOnClickListener {
            manejarDialog()
        }



        listaEstudios = bbdd.estudiosDAO().findByUsuario(idUsuario) as ArrayList<Estudios>
        listaTemporal = ArrayList(listaEstudios)

        rvEstudios = findViewById<RecyclerView>(R.id.rvEstudios)

        rellenarRV()


    }

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

    private fun rellenarRV() {
        rvEstudios.layoutManager = LinearLayoutManager(this)
        var adapter = AdapterEstudios(listaTemporal)

        adapter.setOnItemClickListener(object : AdapterEstudios.OnItemClickListener{
            override fun onItemClick(position: Int) {
                cambiarActivity(position)
            }
        })

        rvEstudios.adapter = adapter
    }

    private fun cambiarActivity(position: Int) {
        var idEstudios = listaEstudios[position].idEstudios
        Toast.makeText(this, (listaEstudios[position]).toString(), Toast.LENGTH_SHORT).show()
    }

    private fun registrar() {
        var s = ""

        try {
            val nombre = etNombre.text.toString()
            val curso = Integer.parseInt(etCurso.text.toString())

            if (etNombre.text.isNotEmpty() && etCurso.text.isNotEmpty()) {

                val estudios =  Estudios(0, nombre = nombre, curso = curso, idUsuario = idUsuario)
                bbdd.estudiosDAO().save(estudios = estudios)

                listaEstudios.add(estudios)
                rellenarRV()

                s = "Estudios guardados correctamente"

            }
            else
                s = ""


        }
        catch (e: Exception) {
            System.err.println(e.message)
            s = "Error al guardar los estudios"
        }

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

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