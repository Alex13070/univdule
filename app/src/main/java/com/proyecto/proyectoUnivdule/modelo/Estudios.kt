package com.proyecto.proyectoUnivdule.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "estudios",
    foreignKeys = [ForeignKey(
        entity = Usuario::class,
        parentColumns = arrayOf("id_usuario"),
        childColumns = arrayOf("id_usuario"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class Estudios(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_estudios")
    var idEstudios: Int,

    @ColumnInfo(name = "nombre")
    var nombre: String,

    @ColumnInfo(name = "curso")
    var curso: Int,

    @ColumnInfo(name = "id_usuario")
    var idUsuario: Int
) : Serializable{

}
