package com.example.appcorpaliv_grupo11.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * VIEWMODEL DE AUTENTICACIÓN Y CONTROL DE SESIÓN
 *
 * Gestiona la lógica de negocio para el inicio y cierre de sesión de los funcionarios/administradores.
 * Administra el estado de las credenciales de entrada (`usuarioInput`, `passwordInput`) y expone
 * flujos observables (`StateFlow`) para notificar el estado de autenticación a la UI.
 */
class AuthViewModel : ViewModel() {

    // =========================================================================
    // ESTADOS DEL FORMULARIO DE LOGIN (CAMPOS DE ENTRADA)
    // Se utiliza mutableStateOf para vincular reactivamente los campos de texto en Compose.
    // 'private set' garantiza encapsulación: la UI lee las variables pero no las modifica directo.
    // =========================================================================

    /**
     * Guarda el nombre de usuario ingresado en el campo de texto.
     * `private set`: Evita la modificación directa desde las vistas (solo modificable mediante `onUsuarioChanged`).
     */
    var usuarioInput by mutableStateOf("")
        private set

    /**
     * Guarda la contraseña ingresada en el campo de texto.
     * `private set`: Protege la variable para que solo se actualice mediante `onPasswordChanged`.
     */
    var passwordInput by mutableStateOf("")
        private set

    // =========================================================================
    // ESTADOS DE SESIÓN Y ERRORES (UI STATE Y CONTROL DE FLUJO)
    // Se utiliza StateFlow para exponer un estado observable, seguro e inmutable a las pantallas.
    // =========================================================================

    /** Estado interno mutable de tipo [MutableStateFlow] que indica si el usuario tiene una sesión activa. */
    private val _isLoggedIn = MutableStateFlow(false)

    /**
     * Estado público e inmutable de tipo [StateFlow] observado por la UI.
     * `asStateFlow()` convierte el flujo mutable interno en una versión de solo lectura.
     */
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    /** Estado interno mutable de tipo [MutableStateFlow] para almacenar mensajes de error durante el login. */
    private val _loginError = MutableStateFlow<String?>(null)

    /** Estado público observable de solo lectura para renderizar mensajes de error en la UI. */
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    // =========================================================================
    // FUNCIONES DE ACTUALIZACIÓN DE CAMPOS
    // =========================================================================

    /**
     * Actualiza el valor del nombre de usuario ingresado y limpia cualquier mensaje de error activo.
     *
     * @param nuevoUsuario Cadena de texto recibida desde el campo de entrada de la UI.
     */
    fun onUsuarioChanged(nuevoUsuario: String) {
        usuarioInput = nuevoUsuario
        _loginError.value = null
    }

    /**
     * Actualiza el valor de la contraseña ingresada y limpia cualquier mensaje de error activo.
     *
     * @param nuevaPass Cadena de texto recibida desde el campo de contraseña de la UI.
     */
    fun onPasswordChanged(nuevaPass: String) {
        passwordInput = nuevaPass
        _loginError.value = null
    }

    // =========================================================================
    // LÓGICA DE AUTENTICACIÓN (INICIO Y CIERRE DE SESIÓN)
    // =========================================================================

    /**
     * Valida los campos e intenta iniciar sesión comparándolos con las credenciales del sistema.
     *
     * @param onSuccess Función lambda / Callback opcional que se ejecuta tras una autenticación exitosa (ej. redirigir).
     */
    fun iniciarSesion(onSuccess: () -> Unit = {}) {
        // 1. Validar que los campos no estén vacíos o contengan solo espacios en blanco
        if (usuarioInput.isBlank() || passwordInput.isBlank()) {
            _loginError.value = "Por favor ingresa usuario y contraseña."
            return
        }

        // 2. Validación de credenciales fijas de prueba para funcionarios ("admin" / "1234")
        if (usuarioInput.trim().lowercase() == "admin" && passwordInput == "1234") {
            _isLoggedIn.value = true
            _loginError.value = null

            // Limpieza de campos de entrada por seguridad tras un ingreso exitoso
            usuarioInput = ""
            passwordInput = ""

            // Invoca la función callback de éxito enviada desde la UI
            onSuccess()
        } else {
            // Asigna el mensaje de error si las credenciales no coinciden
            _loginError.value = "Credenciales incorrectas. Verifica tus datos."
        }
    }

    /**
     * Cierra la sesión activa del usuario y restablece los valores del formulario.
     */
    fun cerrarSesion() {
        _isLoggedIn.value = false
        usuarioInput = ""
        passwordInput = ""
    }
}