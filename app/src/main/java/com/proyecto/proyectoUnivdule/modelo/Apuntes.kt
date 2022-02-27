package com.proyecto.proyectoUnivdule.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "apuntes",
    foreignKeys = [ForeignKey(
        entity = Asignatura::class,
        parentColumns = arrayOf("id_asignatura"),
        childColumns = arrayOf("id_asignatura"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
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
) : Serializable{
}
