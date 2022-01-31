package com.proyecto.proyectoUnivdule.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apuntes")
data class Apuntes(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_apuntes")
    var idApuntes: Int,

    @ColumnInfo(name = "nombre")
    var nombre: String,

    @ColumnInfo(name = "direccion")
    var direccion: String,

    @ColumnInfo(name = "id_asignatura")
    var idAsignatura: Int
) {
}
