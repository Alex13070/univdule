package com.proyecto.proyectoUnivdule.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asignatura")
data class Asignatura(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_asignatura")
    var idAsignatura: Int,

    @ColumnInfo(name = "nombre")
    var nombre: String,

    @ColumnInfo(name = "id_estudios")
    var idEstudios: Int,

    @ColumnInfo(name = "id_evento")
    var idEvento: Int
){
}
