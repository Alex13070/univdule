package com.proyecto.proyectoUnivdule.modelo


data class Tarea(
    var idTarea: Int,
    var nombre:String,
    var contenido:String,
    var idAsignatura:Int,
    var idFecha:Int
){
}