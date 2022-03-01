package com.proyecto.proyectoUnivdule.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "asignatura"/*,
    foreignKeys = [
        ForeignKey(
            entity = Estudios::class,
            parentColumns = arrayOf("id_estudios"),
            childColumns = arrayOf("id_estudios"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Fecha::class,
            parentColumns = arrayOf("id_fecha"),
            childColumns = arrayOf("id_evento"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]*/
)
data class Asignatura(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_asignatura")
    var idAsignatura: Int,

    @ColumnInfo(name = "nombre")
    var nombre: String,

    @ColumnInfo(name = "id_estudios")
    var idEstudios: Int,
/*
    @ColumnInfo(name = "id_evento")
    var idEvento: Int
*/
) : Serializable{
}
