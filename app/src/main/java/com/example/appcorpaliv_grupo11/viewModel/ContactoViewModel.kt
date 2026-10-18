package com.example.appcorpaliv_grupo11.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel encargado de gestionar la lógica de negocios, los estados de los campos
 * y la validación del formulario de contacto para Corpaliv / Alba Lab.
 */
class ContactoViewModel : ViewModel() {

    // --- ESTADOS DE LOS CAMPOS DE TEXTO ---
    // Almacenan el texto ingresado por el usuario en cada campo de forma reactiva.
    var nombre by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var asunto by mutableStateOf("")
        private set

    var mensaje by mutableStateOf("")
        private set

    // --- ESTADOS DE FLUJO (UI STATE Y VALIDACIONES) ---
    // Controla si el formulario ya fue enviado exitosamente para mostrar la pantalla de confirmación.
    private val _isSubmitted = MutableStateFlow(false)
    val isSubmitted: StateFlow<Boolean> = _isSubmitted.asStateFlow()

    // Almacena el mensaje de error actual en caso de que la validación falle.
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // --- FUNCIONES DE ACTUALIZACIÓN DE CAMPOS ---
    /** Actualiza el nombre ingresado y limpia cualquier error previo. */
    fun onNombreChanged(nuevoNombre: String) {
        nombre = nuevoNombre
        _errorMessage.value = null
    }

    /** Actualiza el correo electrónico y limpia el mensaje de error. */
    fun onEmailChanged(nuevoEmail: String) {
        email = nuevoEmail
        _errorMessage.value = null
    }

    /** Actualiza el asunto del mensaje. */
    fun onAsuntoChanged(nuevoAsunto: String) {
        asunto = nuevoAsunto
        _errorMessage.value = null
    }

    /** Actualiza el contenido principal del mensaje. */
    fun onMensajeChanged(nuevoMensaje: String) {
        mensaje = nuevoMensaje
        _errorMessage.value = null
    }

    // --- LÓGICA DE VALIDACIÓN Y ENVÍO ---
    /**
     * Valida que los campos obligatorios (*) estén llenos y que el correo tenga un formato válido.
     * Si todo es correcto, marca el formulario como enviado.
     */
    fun enviarFormulario(onSuccess: () -> Unit) {
        if (nombre.isBlank() || email.isBlank() || mensaje.isBlank()) {
            _errorMessage.value = "Por favor completa los campos obligatorios (*)."
            return
        }

        // Comprueba si el correo cumple con el estándar de email válido
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorMessage.value = "Por favor ingresa un correo electrónico válido."
            return
        }

        _errorMessage.value = null
        _isSubmitted.value = true
        onSuccess()
    }

    /**
     * Restablece todos los campos y estados del formulario a sus valores iniciales
     * para permitir el envío de un nuevo mensaje.
     */
    fun reiniciarFormulario() {
        nombre = ""
        email = ""
        asunto = ""
        mensaje = ""
        _isSubmitted.value = false
        _errorMessage.value = null
    }
}