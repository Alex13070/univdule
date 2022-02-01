package com.proyecto.proyectoUnivdule.daos

import androidx.room.*
import com.proyecto.proyectoUnivdule.modelo.Asignatura

@Dao
interface AsignaturaDAO {

    @Query("SELECT * FROM asignatura")
    fun findAll(): List<Asignatura>

    @Query("SELECT * FROM asignatura WHERE id_asignatura = :id_asignatura")
    fun findById(id_asignatura: String): Asignatura

    @Delete
    fun delete(asignatura: Asignatura)

    @Update
    fun update(asignatura: Asignatura)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(asignatura: Asignatura)

    @Query("SELECT * FROM asignatura WHERE id_estudios = :id_estudios")
    fun findByEstudios(id_estudios: Int): List<Asignatura>
}