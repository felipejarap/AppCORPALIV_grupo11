package com.example.appcorpaliv_grupo11.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de datos que representa un registro de mensaje de contacto en la base de datos Room.
 * Mapea directamente sus atributos a columnas de la tabla 'contactos' en SQLite.
 */
@Entity(tableName = "contactos") // Define el nombre explícito de la tabla en SQLite
data class ContactoEntity(

    /**
     * Identificador único y clave primaria (Primary Key) del mensaje.
     * 'autoGenerate = true' delega a SQLite la generación automática incremental de IDs únicos.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /** Nombre completo del remitente que envió el mensaje. */
    val nombre: String,

    /** Correo electrónico de contacto del remitente. */
    val email: String,

    /** Asunto o título descriptivo del mensaje. */
    val asunto: String,

    /** Cuerpo del mensaje o consulta enviada. */
    val mensaje: String,

    /**
     * Estado actual del mensaje en la plataforma ("Pendiente", "Contestado", "Rechazado").
     * Si no se especifica al instanciar el objeto, tomará "Pendiente" por defecto.
     */
    val estado: String = "Pendiente"
)