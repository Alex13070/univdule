package com.proyecto.proyectoUnivdule.daos

import androidx.room.*
import com.proyecto.proyectoUnivdule.modelo.Fecha

@Dao
interface FechaDAO {
    @Query("SELECT * FROM fecha")
    fun findAll(): List<Fecha>

    @Query("SELECT * FROM fecha WHERE id_fecha = :id_fecha")
    fun findById(id_fecha: String): Fecha

    @Delete
    fun delete(fecha: Fecha);

    @Update
    fun update(fecha: Fecha)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(fecha: Fecha)

    @Query("SELECT * FROM fecha WHERE id_usuario = :id_usuario")
    fun findByEstudios(id_usuario: Int): List<Fecha>
}