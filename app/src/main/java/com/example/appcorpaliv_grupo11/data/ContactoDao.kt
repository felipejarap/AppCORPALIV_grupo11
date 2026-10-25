package com.example.appcorpaliv_grupo11.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Objeto de Acceso a Datos (DAO) para la entidad [ContactoEntity].
 * Define las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) y consultas SQL
 * para interactuar con la tabla 'contactos' de la base de datos Room.
 */
@Dao
interface ContactoDao {

    /**
     * Inserta un nuevo registro de contacto en la base de datos.
     * Utiliza [OnConflictStrategy.REPLACE] para sobrescribir el registro existente si coincide la Clave Primaria (ID).
     *
     * @param contacto Objeto [ContactoEntity] que contiene los datos del mensaje enviado.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarContacto(contacto: ContactoEntity)

    /**
     * Obtiene todos los contactos registrados en la tabla, ordenados descendentemente por ID
     * (los mensajes más recientes aparecen primero).
     *
     * Devuelve un [Flow] que emite automáticamente la lista actualizada en tiempo real
     * cada vez que ocurre un cambio en la tabla de la base de datos.
     */
    @Query("SELECT * FROM contactos ORDER BY id DESC")
    fun obtenerTodosLosContactos(): Flow<List<ContactoEntity>>

    /**
     * Busca y obtiene un contacto específico según su identificador único ([id]).
     *
     * @param id Identificador clave primaria del contacto a consultar.
     * @return El [ContactoEntity] encontrado o `null` si no existe coincidencia.
     */
    @Query("SELECT * FROM contactos WHERE id = :id")
    suspend fun obtenerContactoPorId(id: Int): ContactoEntity?

    /**
     * Actualiza los datos de un contacto existente en la tabla (ej. cambio de estado "Pendiente" a "Contestado").
     *
     * @param contacto Objeto [ContactoEntity] con los datos actualizados según su ID.
     */
    @Update
    suspend fun actualizarContacto(contacto: ContactoEntity)

    /**
     * Elimina de forma permanente un registro específico de la tabla de contactos.
     *
     * @param contacto Objeto [ContactoEntity] a remover de la base de datos.
     */
    @Delete
    suspend fun eliminarContacto(contacto: ContactoEntity)

    /**
     * Obtiene una lista de contactos filtrados según su estado ("Pendiente", "Contestado", "Rechazado"),
     * ordenados descendentemente por ID.
     *
     * @param estado Criterio de estado por el cual filtrar los mensajes.
     * @return [Flow] observable en tiempo real con la lista de contactos filtrada.
     */
    @Query("SELECT * FROM contactos WHERE estado = :estado ORDER BY id DESC")
    fun obtenerPorEstado(estado: String): Flow<List<ContactoEntity>>
}