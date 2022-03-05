package com.proyecto.proyectoUnivdule.daos

import androidx.room.*
import com.proyecto.proyectoUnivdule.modelo.Tarea

@Dao
interface TareaDAO {

    @Query("SELECT * FROM tarea")
    fun findAll(): List<Tarea>

    @Query("SELECT * FROM tarea WHERE id_tarea = :id_tarea")
    fun findById(id_tarea: Int): Tarea

    @Delete
    fun delete(tarea: Tarea)

    @Update
    fun update(tarea: Tarea)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(tarea: Tarea)

    @Query("SELECT * FROM tarea WHERE id_asignatura IN (SELECT id_asignatura FROM asignatura WHERE id_estudios IN (SELECT id_estudios FROM estudios WHERE id_usuario = :id_usuario))")
    fun findByUsuario(id_usuario: Int): List<Tarea>



}