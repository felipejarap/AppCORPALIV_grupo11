package com.example.appcorpaliv_grupo11.model

/**
 * MODELO DE ERRORES DE VALIDACIÓN (DATA CLASS)
 *
 * Contiene los mensajes de error para cada campo del formulario.
 * Si un campo es válido, la propiedad conserva su valor por defecto `null`.
 * Si el campo falla en la validación, la propiedad recibe el texto explicativo del error.
 */
data class ContactoErrores(

    /** Mensaje de error específico para el campo del nombre completo. */
    val nombreError: String? = null,

    /** Mensaje de error específico para el campo de correo electrónico. */
    val emailError: String? = null,

    /** Mensaje de error específico para el campo de teléfono (9 dígitos). */
    val telefonoError: String? = null,

    /** Mensaje de error específico para el campo o selector de asunto. */
    val asuntoError: String? = null,

    /** Mensaje de error específico para el cuerpo o contenido del mensaje. */
    val mensajeError: String? = null,

    /** Mensaje de error de ámbito general (para fallos no ligados a un campo específico). */
    val generalError: String? = null
)