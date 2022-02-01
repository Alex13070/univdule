package com.proyecto.proyectoUnivdule.administracionBBDD

import android.app.Application
import androidx.room.Room

class UnivduleApp: Application() {

    val room = Room.databaseBuilder(this, UnivduleDB::class.java, "univdule").build()
}
