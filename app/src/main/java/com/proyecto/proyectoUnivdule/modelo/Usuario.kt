package com.proyecto.proyectoUnivdule.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "usuario"/*,indices = [Index(value = ["nombre_usuario"], unique = true)]*/)
data class Usuario(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_usuario")
    var idUsuario: Int,

    @ColumnInfo(name = "nombre_usuario")
    var nombreUsuario: String,

    @ColumnInfo(name = "nombre")
    var nombre: String,

    @ColumnInfo(name = "clave")
    var clave: String
) : Serializable {
}