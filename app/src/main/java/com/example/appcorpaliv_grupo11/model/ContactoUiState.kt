package com.example.appcorpaliv_grupo11.model

/**
 * CLASE DE ESTADO COMPLETO DE LA UI (UI STATE - PATRÓN UDF)
 *
 * Centraliza todo el estado mutable de la pantalla del formulario en un único objeto inmutable.
 * Implementa el flujo de datos unidireccional (Unidirectional Data Flow - UDF), permitiendo que la UI
 * en Jetpack Compose reaccione automáticamente a cualquier cambio y conserve el estado ante
 * rotaciones de pantalla.
 */
data class ContactoUiState(

    /** Almacena el texto introducido en el campo de texto del Nombre del remitente. */
    val nombre: String = "",

    /** Almacena el texto introducido en el campo del Correo Electrónico. */
    val email: String = "",

    /** Almacena el texto introducido en el campo del Teléfono móvil. */
    val telefono: String = "",

    /** Almacena la opción actual seleccionada en el menú o ComboBox de Asunto. */
    val asunto: String = "",

    /** Almacena el texto introducido en el área de texto del Mensaje principal. */
    val mensaje: String = "",

    /**
     * Instancia de [ContactoErrores] que agrupa los mensajes de validación individuales.
     * Se inicializa con una instancia vacía (sin errores) por defecto.
     */
    val errores: ContactoErrores = ContactoErrores(),

    /**
     * Bandera (Flag) booleana de éxito en el envío del formulario.
     * - `false`: Muestra el formulario activo listo para edición.
     * - `true`: Despliega el componente visual o tarjeta de confirmación de éxito.
     */
    val isSubmitted: Boolean = false
)