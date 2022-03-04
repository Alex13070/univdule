package com.proyecto.proyectoUnivdule

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.proyecto.proyectoUnivdule.administracionBBDD.UnivduleDB
import com.proyecto.proyectoUnivdule.modelo.Tarea
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CrearTareaActivity : AppCompatActivity() {

    //Base de datos
    private lateinit var bbdd: UnivduleDB
    private var idAsignatura: Int = 0

    //Campos del formulario
    private lateinit var etNombre: EditText
    private lateinit var etFecha: EditText
    private lateinit var etContenido: EditText

    //Boton
    private lateinit var btnAdd: Button

    //Constructor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_tarea)

        //Base de datos
        bbdd = Room.databaseBuilder(this, UnivduleDB::class.java, "univdule").allowMainThreadQueries().fallbackToDestructiveMigration().build()
        idAsignatura = intent.getIntExtra("id_asignatura", -1)

        //Inicializar variables
        etNombre = findViewById(R.id.etAddTareaNombre)
        etFecha = findViewById(R.id.etAddTareaFecha)
        etContenido = findViewById(R.id.etAddTareaContenido)

        btnAdd = findViewById(R.id.btnAddTareaAdd)

        etFecha.setOnClickListener {
            val fecha = DatePickerFragment {year, month, day -> rellenarFecha(year, month, day) }
            fecha.show(supportFragmentManager, "DatePicker")
        }

        btnAdd.setOnClickListener {
            addTarea()
        }

    }

    private fun rellenarFecha(year: Int, month: Int, day: Int) {
        var fecha = ""

        fecha += if (day < 10) "0"+day else day
        fecha += "/"
        fecha += if (month < 10) "0"+(month+1) else (month+1)
        fecha += "/"
        fecha += year

        etFecha.setText(fecha)

    }

    private fun addTarea() {
        var msg = ""
        if (etNombre.text.isNotEmpty() && etFecha.text.isNotEmpty() && etContenido.text.isNotEmpty()) {
            val nombre = etNombre.text.toString()
            val fecha = LocalDate.parse(etFecha.text.toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            val contenido = etNombre.text.toString()

            System.err.println(fecha.toString())

            if (fecha.isAfter(LocalDate.now())) {

                val tarea = Tarea(idTarea = 0, nombre = nombre,  contenido = contenido, fecha = fecha.toString(), idAsignatura = idAsignatura)

                try {
                    bbdd.tareaDAO().save(tarea = tarea)
                    msg = "Tarea guardada correctamente"
                }
                catch (e: Exception) {
                    msg = "Error al guardar la fecha"
                    System.err.println(e.message)
                }


            }
            else
                msg = "La fecha tiene que ser mayor a la actual"

        }
        else
            msg = "Faltan campos por rellenar"

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    class DatePickerFragment(val listener: (year: Int, month: Int, day: Int) -> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener{

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            //Coger fecha
            val calendar: Calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            return DatePickerDialog(requireActivity(), this, year, month, day)
        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
            listener(year, month, day)
        }

    }
}