package com.proyecto.proyectoUnivdule.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarea")
data class Tarea(

    @PrimaryKey(autoGenerate = true)
    var idTarea: Int,

    @ColumnInfo(name = "nombre")
    var nombre:String,

    @ColumnInfo(name = "contenido")
    var contenido:String,

    @ColumnInfo(name = "id_asignatura")
    var idAsignatura:Int,

    @ColumnInfo(name = "id_fecha")
    var idFecha:Int
){
}