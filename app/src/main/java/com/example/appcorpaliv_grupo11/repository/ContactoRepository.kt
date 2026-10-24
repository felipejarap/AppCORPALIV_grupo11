package com.example.appcorpaliv_grupo11.repository

import com.example.appcorpaliv_grupo11.data.ContactoDao
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import kotlinx.coroutines.flow.Flow

class ContactoRepository(private val contactoDao: ContactoDao) {

    // Flujo continuo con todos los contactos
    val listaContactos: Flow<List<ContactoEntity>> = contactoDao.obtenerTodosLosContactos()

    suspend fun guardarContacto(contacto: ContactoEntity) {
        contactoDao.guardarContacto(contacto)
    }

    suspend fun actualizarContacto(contacto: ContactoEntity) {
        contactoDao.actualizarContacto(contacto)
    }

    suspend fun eliminarContacto(contacto: ContactoEntity) {
        contactoDao.eliminarContacto(contacto)
    }

    suspend fun obtenerPorId(id: Int): ContactoEntity? {
        return contactoDao.obtenerContactoPorId(id)
    }
}