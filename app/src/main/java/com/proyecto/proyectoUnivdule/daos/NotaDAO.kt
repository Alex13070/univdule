package com.proyecto.proyectoUnivdule.daos

import androidx.room.*
import com.proyecto.proyectoUnivdule.modelo.Nota

@Dao
interface NotaDAO {

    @Query("SELECT * FROM nota")
    fun findAll(): List<Nota>

    @Query("SELECT * FROM nota WHERE id_notas = :id_nota")
    fun findById(id_nota: String): Nota

    @Delete
    fun delete(nota: Nota)

    @Update
    fun update(nota: Nota)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(nota: Nota)

    @Query("SELECT * FROM nota WHERE id_asignatura = :id_asignatura")
    fun findByEstudios(id_asignatura: Int): List<Nota>

}