package com.proyecto.proyectoUnivdule.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "fecha",
    foreignKeys = [ForeignKey(
        entity = Usuario::class,
        parentColumns = arrayOf("id_usuario"),
        childColumns = arrayOf("id_usuario"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
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

) : Serializable{
}