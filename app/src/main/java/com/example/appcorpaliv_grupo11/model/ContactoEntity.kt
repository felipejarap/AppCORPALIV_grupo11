package com.example.appcorpaliv_grupo11.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de datos que representa un registro de mensaje de contacto en la base de datos Room.
 */
@Entity(tableName = "contactos")
data class ContactoEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /** Nombre completo del remitente. */
    val nombre: String,

    /** Correo electrónico de contacto. */
    val email: String,

    /** Teléfono móvil de contacto (9 dígitos). */
    val telefono: String = "",

    /** Asunto o categoría seleccionada del mensaje. */
    val asunto: String,

    /** Cuerpo del mensaje o consulta enviada. */
    val mensaje: String,

    /** Estado actual del mensaje ("Pendiente", "Contestado", "Rechazado"). */
    val estado: String = "Pendiente"
)