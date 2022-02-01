package com.proyecto.proyectoUnivdule.daos

import androidx.room.*
import com.proyecto.proyectoUnivdule.modelo.Usuario

@Dao
interface UsuarioDAO {

    @Query("SELECT * FROM usuario")
    fun findAll(): List<Usuario>

    @Query("SELECT * FROM usuario WHERE id_usuario = :id_usuario")
    fun findById(id_usuario: Int): Usuario

    @Delete
    fun delete(usuario: Usuario);

    @Update
    fun update(usuario: Usuario)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(usuario: Usuario)
}