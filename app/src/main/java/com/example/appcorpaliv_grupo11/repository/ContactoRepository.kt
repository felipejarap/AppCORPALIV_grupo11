package com.example.appcorpaliv_grupo11.repository

import android.util.Log
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import com.example.appcorpaliv_grupo11.network.RetrofitClient
import com.example.appcorpaliv_grupo11.network.toDto
import com.example.appcorpaliv_grupo11.network.toEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * REPOSITORIO DE CONTACTO (100% MICROSERVICIO SPRING BOOT + MYSQL)
 *
 * Gestiona las operaciones CRUD directamente contra la API REST mediante Retrofit
 * y mantiene el estado reactivo en memoria para la interfaz Compose.
 */
class ContactoRepository {

    private val api = RetrofitClient.apiService

    private val _listaContactos = MutableStateFlow<List<ContactoEntity>>(emptyList())
    val listaContactos: StateFlow<List<ContactoEntity>> = _listaContactos.asStateFlow()

    /**
     * Descarga todos los registros desde el backend y actualiza el flujo en memoria.
     */
    suspend fun sincronizarDesdeServidor() {
        val response = api.obtenerTodos()
        if (response.isSuccessful) {
            val listaRemota = response.body()
                ?.map { it.toEntity() }
                ?.sortedByDescending { it.id }
                ?: emptyList()
            _listaContactos.value = listaRemota
        } else {
            Log.e("ContactoRepository", "Error GET HTTP ${response.code()}")
            throw Exception("No se pudo obtener la información solicitada.")
        }
    }

    /**
     * Envía un nuevo contacto al backend (POST) y agrega la entidad devuelta.
     */
    suspend fun guardarContacto(contacto: ContactoEntity) {
        val response = api.crearContacto(contacto.toDto(incluirId = false))
        if (response.isSuccessful && response.body() != null) {
            val creado = response.body()!!.toEntity()
            _listaContactos.update { listaActual ->
                listOf(creado) + listaActual
            }
        } else {
            Log.e("ContactoRepository", "Error POST HTTP ${response.code()}")
            throw Exception("No se pudo registrar la información.")
        }
    }

    /**
     * Actualiza un contacto existente en el backend (PUT) y refresca el ítem en memoria.
     */
    suspend fun actualizarContacto(contacto: ContactoEntity) {
        val response = api.actualizarContacto(contacto.id, contacto.toDto(incluirId = true))
        if (response.isSuccessful && response.body() != null) {
            val actualizado = response.body()!!.toEntity()
            _listaContactos.update { listaActual ->
                listaActual.map { if (it.id == actualizado.id) actualizado else it }
            }
        } else {
            Log.e("ContactoRepository", "Error PUT HTTP ${response.code()}")
            throw Exception("No se pudo actualizar la información.")
        }
    }

    /**
     * Elimina el contacto en el backend (DELETE) y lo remueve de la lista en memoria.
     */
    suspend fun eliminarContacto(contacto: ContactoEntity) {
        val response = api.eliminarContacto(contacto.id)
        if (response.isSuccessful) {
            _listaContactos.update { listaActual ->
                listaActual.filterNot { it.id == contacto.id }
            }
        } else {
            Log.e("ContactoRepository", "Error DELETE HTTP ${response.code()}")
            throw Exception("No se pudo eliminar el registro.")
        }
    }

    /**
     * Busca un contacto por su ID en la lista cargada desde el backend.
     */
    fun obtenerPorId(id: Int): ContactoEntity? {
        return _listaContactos.value.find { it.id == id }
    }
}