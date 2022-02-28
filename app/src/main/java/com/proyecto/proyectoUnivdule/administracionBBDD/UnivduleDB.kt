package com.proyecto.proyectoUnivdule.administracionBBDD

import androidx.room.Database
import androidx.room.RoomDatabase
import com.proyecto.proyectoUnivdule.daos.*
import com.proyecto.proyectoUnivdule.modelo.*


@Database(
    entities = [Apuntes::class, Asignatura::class, Estudios::class, Fecha::class, Nota::class, Tarea::class, Usuario::class],
    version = 15
)
abstract class UnivduleDB: RoomDatabase() {

    abstract fun apuntesDAO(): ApuntesDAO
    abstract fun asignaturaDAO(): AsignaturaDAO
    abstract fun estudiosDAO(): EstudiosDAO
    abstract fun fechaDAO(): FechaDAO
    abstract fun notaDAO(): NotaDAO
    abstract fun tareaDAO(): TareaDAO
    abstract fun usuarioDAO(): UsuarioDAO

}