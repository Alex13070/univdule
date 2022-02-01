package com.proyecto.proyectoUnivdule.daos

import androidx.room.*
import com.proyecto.proyectoUnivdule.modelo.Tarea

@Dao
interface TareaDAO {

    @Query("SELECT * FROM tarea")
    fun findAll(): List<Tarea>

    @Query("SELECT * FROM tarea WHERE id_tarea = :id_tarea")
    fun findById(id_tarea: String): Tarea

    @Delete
    fun delete(tarea: Tarea)

    @Update
    fun update(tarea: Tarea)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(tarea: Tarea)

    @Query("SELECT * FROM tarea WHERE id_asignatura = :id_asignatura")
    fun findByEstudios(id_asignatura: Int): List<Tarea>

}