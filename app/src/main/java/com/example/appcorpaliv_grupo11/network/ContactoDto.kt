package com.example.appcorpaliv_grupo11.network

import com.example.appcorpaliv_grupo11.model.ContactoEntity

data class ContactoDto(
    val id: Int? = null,
    val nombre: String? = "",
    val email: String? = "",
    val telefono: String? = "",
    val asunto: String? = "",
    val mensaje: String? = "",
    val estado: String? = "Pendiente"
)

fun ContactoDto.toEntity(): ContactoEntity {
    val estadoNormalizado = when (estado?.trim()?.lowercase()) {
        "contestado" -> "Contestado"
        "rechazado" -> "Rechazado"
        else -> "Pendiente"
    }

    return ContactoEntity(
        id = id ?: 0,
        nombre = nombre ?: "Sin nombre",
        email = email ?: "",
        telefono = telefono ?: "",
        asunto = asunto ?: "Sin asunto",
        mensaje = mensaje ?: "",
        estado = estadoNormalizado
    )
}

fun ContactoEntity.toDto(incluirId: Boolean = false): ContactoDto {
    return ContactoDto(
        id = if (incluirId && id != 0) id else null,
        nombre = nombre,
        email = email,
        telefono = telefono,
        asunto = asunto,
        mensaje = mensaje,
        estado = estado
    )
}