package com.example.appcorpaliv_grupo11.model

/**
 * MODELO DE DOMINIO DE CONTACTO
 *
 * Representa un mensaje o consulta gestionada a través del microservicio Spring Boot + MySQL.
 * Se define como una `data class` inmutable de Kotlin para garantizar seguridad de estado
 * en Jetpack Compose y facilitar métodos como `copy()`, `equals()` y `toString()`.
 */
data class ContactoEntity(

    /**
     * Identificador único asignado por la base de datos MySQL en el backend.
     * Se inicializa en 0 antes de enviarse por POST al microservicio.
     */
    val id: Int = 0,

    /** Nombre completo del remitente ingresado en el formulario. */
    val nombre: String,

    /** Correo electrónico del remitente utilizado para respuestas vía Email. */
    val email: String,

    /**
     * Teléfono móvil del remitente (formato de 9 dígitos).
     * Se asigna una cadena vacía ("") por defecto en caso de no estar informado.
     */
    val telefono: String = "",

    /** Asunto o categoría temática seleccionada en el formulario. */
    val asunto: String,

    /** Contenido textual completo del mensaje o consulta enviada. */
    val mensaje: String,

    /**
     * Estado administrativo del mensaje.
     * Valores posibles: "Pendiente", "Contestado", "Rechazado".
     */
    val estado: String = "Pendiente"
)