package com.proyecto.proyectoUnivdule.daos

import androidx.room.*
import com.proyecto.proyectoUnivdule.modelo.Apuntes

@Dao
interface ApuntesDAO {

    @Query("SELECT * FROM apuntes")
    fun findAll(): List<Apuntes>

    @Query("SELECT * FROM apuntes WHERE id_apuntes = :id_apuntes")
    fun findById(id_apuntes: String): Apuntes

    @Delete
    fun delete(apuntes: Apuntes)

    @Update
    fun update(apuntes: Apuntes)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(apuntes: Apuntes)

    @Query("SELECT * FROM apuntes WHERE id_asignatura = :id_asignatura")
    fun findByEstudios(id_asignatura: Int): List<Apuntes>
}