package com.proyecto.proyectoUnivdule.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fecha")
data class Fecha(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_fecha")
    var idFecha:Int,

    @ColumnInfo(name = "fecha")
    var fecha:String,

    @ColumnInfo(name = "contenido")
    var contenido:String,

    @ColumnInfo(name = "id_usuario")
    var idUsuario:Int

) {
}