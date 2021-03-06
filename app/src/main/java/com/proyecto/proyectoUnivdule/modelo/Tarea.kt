package com.proyecto.proyectoUnivdule.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tarea",
    foreignKeys = [ForeignKey(
        entity = Asignatura::class,
        parentColumns = arrayOf("id_asignatura"),
        childColumns = arrayOf("id_asignatura"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )/*,
        ForeignKey(
        entity = Fecha::class,
        parentColumns = arrayOf("id_fecha"),
        childColumns = arrayOf("id_fecha"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )*/
    ]
)
data class Tarea(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_tarea")
    var idTarea: Int,

    @ColumnInfo(name = "nombre")
    var nombre:String,

    @ColumnInfo(name = "contenido")
    var contenido:String,

    @ColumnInfo(name = "id_asignatura")
    var idAsignatura:Int,

    @ColumnInfo(name = "fecha")
    var fecha: String

): Serializable{
}