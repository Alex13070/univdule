package com.proyecto.proyectoUnivdule.daos


import androidx.room.*
import com.proyecto.proyectoUnivdule.modelo.Estudios
import java.util.*


@Dao
interface EstudiosDAO {

    @Query("SELECT * FROM estudios")
    fun findAll(): List<Estudios>

    @Query("SELECT * FROM estudios WHERE id_estudios = :id_estudios")
    fun findById(id_estudios: Int): Estudios

    @Delete
    fun delete(estudios: Estudios)

    @Update
    fun update(estudios: Estudios)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(estudios: Estudios)

    @Query("SELECT * FROM estudios WHERE id_usuario = :id_usuario")
    fun findByUsuario(id_usuario: Int): List<Estudios>



}