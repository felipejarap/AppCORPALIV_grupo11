package com.example.appcorpaliv_grupo11.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarContacto(contacto: ContactoEntity)

    @Query("SELECT * FROM contactos ORDER BY id DESC")
    fun obtenerTodosLosContactos(): Flow<List<ContactoEntity>>

    @Query("SELECT * FROM contactos WHERE id = :id")
    suspend fun obtenerContactoPorId(id: Int): ContactoEntity?

    @Update
    suspend fun actualizarContacto(contacto: ContactoEntity)

    @Delete
    suspend fun eliminarContacto(contacto: ContactoEntity)

    // OPTATIVO: Obtener contactos filtrados directamente desde Room por estado
    @Query("SELECT * FROM contactos WHERE estado = :estado ORDER BY id DESC")
    fun obtenerPorEstado(estado: String): Flow<List<ContactoEntity>>
}