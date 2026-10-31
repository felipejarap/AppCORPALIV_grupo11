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
 * INTERFAZ DAO (Data Access Object)
 *
 * Define la interfaz de acceso a datos para la entidad [ContactoEntity].
 * Room genera automáticamente la implementación de estas funciones SQL en tiempo de compilación.
 * Permite ejecutar las operaciones básicas del ciclo de datos CRUD (Crear, Leer, Actualizar, Eliminar).
 */
@Dao
interface ContactoDao {

    /**
     * Inserta un nuevo registro de contacto en la tabla 'contactos'.
     *
     * @param contacto Objeto de tipo [ContactoEntity] a guardar.
     *
     * - @Insert: Le indica a Room que genera una sentencia SQL INSERT.
     * - onConflict = OnConflictStrategy.REPLACE: Si se intenta insertar un objeto con un ID que ya existe,
     *   reemplaza/sobrescribe el registro previo en lugar de lanzar un error.
     * - suspend: Ejecución asíncrona dentro de Corrutinas para no bloquear el hilo principal de la UI.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarContacto(contacto: ContactoEntity)

    /**
     * Obtiene la totalidad de los contactos almacenados en la base de datos.
     *
     * @return [Flow] Lista reactiva observable que emite cambios automáticos en la UI.
     *
     * - @Query("SELECT * FROM contactos ORDER BY id DESC"): Consulta SQL personalizada que extrae todos
     *   los registros ordenados de forma descendente (los más recientes aparecen primero).
     * - Nota: Las funciones que retornan [Flow] NO llevan la palabra clave 'suspend', ya que Flow maneja
     *   la asincronía y reactividad de forma nativa.
     */
    @Query("SELECT * FROM contactos ORDER BY id DESC")
    fun obtenerTodosLosContactos(): Flow<List<ContactoEntity>>

    /**
     * Busca y obtiene un único contacto según su ID clave primaria.
     *
     * @param id Identificador numérico de la clave primaria.
     * @return [ContactoEntity] si existe coincidencia, o `null` en caso contrario.
     *
     * - SQL: "SELECT * FROM contactos WHERE id = :id" (Usa `:id` para vincular el parámetro de Kotlin a la consulta SQL).
     * - suspend: Debe llamarse dentro de una corrutina en un hilo secundario (ej. Dispatchers.IO).
     */
    @Query("SELECT * FROM contactos WHERE id = :id")
    suspend fun obtenerContactoPorId(id: Int): ContactoEntity?

    /**
     * Actualiza la información existente de un contacto (ej: cambiar estado de "Pendiente" a "Contestado").
     *
     * @param contacto Objeto [ContactoEntity] con los valores modificados pero manteniendo el mismo ID.
     *
     * - @Update: Busca el registro en la base de datos coincidiendo por su @PrimaryKey (ID) y actualiza sus columnas.
     * - suspend: Ejecución asíncrona segura.
     */
    @Update
    suspend fun actualizarContacto(contacto: ContactoEntity)

    /**
     * Borra de forma permanente un registro de la base de datos.
     *
     * @param contacto Objeto [ContactoEntity] que se eliminará.
     *
     * - @Delete: Ejecuta la sentencia DELETE basándose en la clave primaria del objeto provisto.
     * - suspend: Ejecución en hilo secundario.
     */
    @Delete
    suspend fun eliminarContacto(contacto: ContactoEntity)

    /**
     * Filtra los contactos almacenados basándose en su estado actual ("Pendiente", "Contestado", "Rechazado").
     *
     * @param estado Criterio de filtrado en formato texto.
     * @return [Flow] observable reactivo en tiempo real filtrado por la condición especificada.
     *
     * - SQL: "SELECT * FROM contactos WHERE estado = :estado ORDER BY id DESC"
     */
    @Query("SELECT * FROM contactos WHERE estado = :estado ORDER BY id DESC")
    fun obtenerPorEstado(estado: String): Flow<List<ContactoEntity>>
}