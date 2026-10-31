package com.example.appcorpaliv_grupo11.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * CLASE DE ENTIDAD (MODELO DE DATOS ROOM)
 *
 * Esta clase mapea directamente una tabla en la base de datos local SQLite llamada 'contactos'.
 * Se define como una `data class` de Kotlin para garantizar la inmutabilidad y ofrecer métodos
 * automáticos como `copy()`, `equals()` y `toString()`.
 */
@Entity(tableName = "contactos")
data class ContactoEntity(

    /**
     * Clave Primaria (Primary Key) única para cada registro de contacto.
     *
     * - @PrimaryKey(autoGenerate = true): Le indica a Room que genere e incremente
     *   automáticamente el valor entero del ID (1, 2, 3...) al insertar un nuevo registro.
     * - val id: Int = 0: Se inicializa en 0 por defecto para que Room sepa que es un registro
     *   nuevo y asigne el ID correspondiente en la base de datos.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /** Nombre completo del remitente ingresado en el formulario. */
    val nombre: String,

    /** Correo electrónico del remitente utilizado para respuestas vía Email. */
    val email: String,

    /**
     * Teléfono móvil del remitente (formato de 9 dígitos).
     * Se asigna una cadena vacía ("") por defecto en caso de que sea un campo opcional.
     */
    val telefono: String = "",

    /** Asunto o categoría temática seleccionada en el formulario. */
    val asunto: String,

    /** Contenido textual completo del mensaje o consulta enviada. */
    val mensaje: String,

    /**
     * Estado administrativo del mensaje.
     *
     * - Valores posibles en la app: "Pendiente", "Contestado", "Rechazado".
     * - Se inicializa con "Pendiente" por defecto al crearse un nuevo registro.
     * - Determina el color dinámico de la tarjeta en la interfaz (Naranja, Verde o Rojo).
     */
    val estado: String = "Pendiente"
)