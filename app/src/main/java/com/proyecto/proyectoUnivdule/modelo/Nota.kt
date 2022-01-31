package com.proyecto.proyectoUnivdule.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nota")
data class Nota(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_notas")
    var idNotas:Int,

    @ColumnInfo(name = "nombre")
    var nombre:String,

    @ColumnInfo(name = "contenido")
    var contenido:String,

    @ColumnInfo(name = "id_asignatura")
    var idAsignatura:Int
) {
}