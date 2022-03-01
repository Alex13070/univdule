package com.proyecto.proyectoUnivdule

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.util.*

class CalendarioActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var botones: ArrayList<Button>

    private lateinit var btnAvanzar: Button
    private lateinit var btnRetroceder: Button
    private lateinit var tvMes: TextView

    private var date = LocalDate.of(LocalDate.now().year, LocalDate.now().monthValue, 1)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)
        botones = ArrayList()

        botones.add(findViewById<Button>(R.id.btn1))
        botones.add(findViewById<Button>(R.id.btn2))
        botones.add(findViewById<Button>(R.id.btn3))
        botones.add(findViewById<Button>(R.id.btn4))
        botones.add(findViewById<Button>(R.id.btn5))
        botones.add(findViewById<Button>(R.id.btn6))
        botones.add(findViewById<Button>(R.id.btn7))
        botones.add(findViewById<Button>(R.id.btn8))
        botones.add(findViewById<Button>(R.id.btn9))
        botones.add(findViewById<Button>(R.id.btn10))
        botones.add(findViewById<Button>(R.id.btn11))
        botones.add(findViewById<Button>(R.id.btn12))
        botones.add(findViewById<Button>(R.id.btn13))
        botones.add(findViewById<Button>(R.id.btn14))
        botones.add(findViewById<Button>(R.id.btn15))
        botones.add(findViewById<Button>(R.id.btn16))
        botones.add(findViewById<Button>(R.id.btn17))
        botones.add(findViewById<Button>(R.id.btn18))
        botones.add(findViewById<Button>(R.id.btn19))
        botones.add(findViewById<Button>(R.id.btn20))
        botones.add(findViewById<Button>(R.id.btn21))
        botones.add(findViewById<Button>(R.id.btn22))
        botones.add(findViewById<Button>(R.id.btn23))
        botones.add(findViewById<Button>(R.id.btn24))
        botones.add(findViewById<Button>(R.id.btn25))
        botones.add(findViewById<Button>(R.id.btn26))
        botones.add(findViewById<Button>(R.id.btn27))
        botones.add(findViewById<Button>(R.id.btn28))
        botones.add(findViewById<Button>(R.id.btn29))
        botones.add(findViewById<Button>(R.id.btn30))
        botones.add(findViewById<Button>(R.id.btn31))
        botones.add(findViewById<Button>(R.id.btn32))
        botones.add(findViewById<Button>(R.id.btn33))
        botones.add(findViewById<Button>(R.id.btn34))
        botones.add(findViewById<Button>(R.id.btn35))
        botones.add(findViewById<Button>(R.id.btn36))
        botones.add(findViewById<Button>(R.id.btn37))
        botones.add(findViewById<Button>(R.id.btn38))
        botones.add(findViewById<Button>(R.id.btn39))
        botones.add(findViewById<Button>(R.id.btn40))
        botones.add(findViewById<Button>(R.id.btn41))
        botones.add(findViewById<Button>(R.id.btn42))

        for (b in botones)
            b.setOnClickListener(this)

        btnAvanzar = findViewById(R.id.btnCalendarioAvanzar)
        btnRetroceder = findViewById(R.id.btnCalendarioRetroceder)
        tvMes = findViewById(R.id.tvCalendarioTexto)

        btnAvanzar.setOnClickListener {
            avanzar()
        }

        btnRetroceder.setOnClickListener {
            retroceder()
        }

        procesarFecha()
    }

    private fun retroceder() {
        date = date.minusMonths(1)
        procesarFecha()
    }

    private fun avanzar() {
        date = date.plusMonths(1)
        procesarFecha()
    }

    override fun onClick(v: View?) {
        var btn: Button = v as Button
        var dia = Integer.parseInt(btn.text.toString())
        var fecha = LocalDate.of(date.year, date.monthValue, dia)
        //TODO("Sistema de notificaciones de la aplicacion")
    }

    private fun procesarFecha() {
        var length = date.month.length(GregorianCalendar().isLeapYear(date.year))
        var diaSemana = date.dayOfWeek.value
        var i = 1
        var dia = 1
        for (b: Button in botones) {
            if (i >= diaSemana && dia <= length){
                b.setEnabled(true)
                b.background.setColorFilter(Color.rgb(214, 215, 215), PorterDuff.Mode.SRC_ATOP)
                b.text = dia.toString()
                dia++
            }
            else{
                b.setEnabled(false)
                b.text = ""
                b.background.setColorFilter(Color.rgb(255, 219, 0), PorterDuff.Mode.SRC_ATOP)
            }
            i++
        }

        tvMes.text = "${date.month.toString()} - ${date.year}"
    }
}