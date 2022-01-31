package com.proyecto.proyectoUnivdule.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estudios")
data class Estudios(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_estudios")
    var idEstudios: Int,

    @ColumnInfo(name = "nombre")
    var nombre: String,

    @ColumnInfo(name = "curso")
    var curso: Int,

    @ColumnInfo(name = "id_usuario")
    var idUsuario: String
){

}
