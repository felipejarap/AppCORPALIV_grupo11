package com.example.appcorpaliv_grupo11.repository

import com.example.appcorpaliv_grupo11.data.ContactoDao
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio encargado de gestionar las operaciones de datos de los mensajes de contacto.
 *
 * Actúa como punto único de acceso a los datos (Single Source of Truth), intermediando entre
 * el DAO de Room ([ContactoDao]) y los ViewModels de la aplicación.
 *
 * @property contactoDao Objeto de acceso a datos para ejecutar operaciones SQLite sobre la entidad Contacto.
 */
class ContactoRepository(private val contactoDao: ContactoDao) {

    /**
     * Flujo reactivo continuo que emite la lista completa de mensajes guardados en Room.
     *
     * Cualquier modificación en la tabla de la base de datos actualizará automáticamente este [Flow].
     */
    val listaContactos: Flow<List<ContactoEntity>> = contactoDao.obtenerTodosLosContactos()

    /**
     * Inserta o reemplaza un mensaje de contacto en la base de datos local de forma asíncrona.
     *
     * @param contacto Registro [ContactoEntity] a persistir.
     */
    suspend fun guardarContacto(contacto: ContactoEntity) {
        contactoDao.guardarContacto(contacto)
    }

    /**
     * Actualiza la información de un contacto existente en Room de forma asíncrona.
     *
     * @param contacto Registro [ContactoEntity] modificado.
     */
    suspend fun actualizarContacto(contacto: ContactoEntity) {
        contactoDao.actualizarContacto(contacto)
    }

    /**
     * Remueve permanentemente un mensaje de contacto de la base de datos.
     *
     * @param contacto Registro [ContactoEntity] a eliminar.
     */
    suspend fun eliminarContacto(contacto: ContactoEntity) {
        contactoDao.eliminarContacto(contacto)
    }

    /**
     * Busca y obtiene un contacto específico según su ID único.
     *
     * @param id Identificador numérico del contacto.
     * @return El [ContactoEntity] correspondiente o `null` si no existe.
     */
    suspend fun obtenerPorId(id: Int): ContactoEntity? {
        return contactoDao.obtenerContactoPorId(id)
    }
}