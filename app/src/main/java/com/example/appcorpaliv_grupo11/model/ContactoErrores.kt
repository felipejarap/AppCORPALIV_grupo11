package com.example.appcorpaliv_grupo11.model

/**
 * Modelo de datos inmutable para representar los mensajes de error de validación
 * en el formulario de contacto.
 *
 * Asigna un mensaje de error específico para cada campo individual.
 */
data class ContactoErrores(

    /** Mensaje de error para el campo del nombre. */
    val nombreError: String? = null,

    /** Mensaje de error para el correo electrónico. */
    val emailError: String? = null,

    /** Mensaje de error para el campo de teléfono (9 dígitos). */
    val telefonoError: String? = null,

    /** Mensaje de error para la selección de asunto (ComboBox). */
    val asuntoError: String? = null,

    /** Mensaje de error para el cuerpo del mensaje. */
    val mensajeError: String? = null,

    /** Mensaje de error global (ej. fallos en la base de datos o red). */
    val generalError: String? = null
)