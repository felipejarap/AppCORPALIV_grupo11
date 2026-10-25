package com.example.appcorpaliv_grupo11.model

/**
 * Clase de datos inmutable (UI State) que encapsula el estado completo de la interfaz
 * de usuario para la pantalla del formulario de contacto.
 *
 * Centraliza los valores de los campos de entrada y el estado de la entrega, facilitando
 * la preservación del estado de la pantalla ante cambios de configuración (como rotaciones)
 * y garantizando un flujo de datos unidireccional (UDF) hacia las vistas en Jetpack Compose.
 */
data class ContactoUiState(

    /** Valor actual ingresado en el campo de texto del nombre. */
    val nombre: String = "",

    /** Valor actual ingresado en el campo de correo electrónico. */
    val email: String = "",

    /** Valor actual ingresado en el campo de asunto. */
    val asunto: String = "",

    /** Valor actual ingresado en el área de texto del mensaje. */
    val mensaje: String = "",

    /**
     * Indica si el formulario fue enviado exitosamente.
     * Cuando es `true`, la interfaz puede reaccionar ocultando el formulario
     * y desplegando la tarjeta de confirmación de éxito.
     */
    val isSubmitted: Boolean = false
)