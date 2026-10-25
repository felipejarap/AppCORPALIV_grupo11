package com.example.appcorpaliv_grupo11.model

/**
 * Modelo de datos inmutable para representar los mensajes de error de validación
 * en el formulario de contacto.
 *
 * Permite gestionar de forma granular la retroalimentación visual en la UI, asignando
 * un mensaje de error específico para cada campo de texto o para fallos generales.
 */
data class ContactoErrores(

    /**
     * Mensaje de error asociado al campo del nombre.
     * Es `null` cuando la validación del nombre es exitosa o no hay errores.
     */
    val nombreError: String? = null,

    /**
     * Mensaje de error asociado al campo del correo electrónico
     * (ej. campo vacío o formato de email inválido).
     */
    val emailError: String? = null,

    /**
     * Mensaje de error asociado al cuerpo principal del mensaje.
     */
    val mensajeError: String? = null,

    /**
     * Mensaje de error global no ligado a un campo específico
     * (ej. fallos en la base de datos local o falta de conectividad).
     */
    val generalError: String? = null
)