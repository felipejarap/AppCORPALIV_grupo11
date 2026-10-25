package com.example.appcorpaliv_grupo11.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel encargado de la autenticación de usuarios (funcionarios/administradores),
 * gestionando el estado de la sesión, la captura de credenciales y la validación de acceso.
 */
class AuthViewModel : ViewModel() {

    // =========================================================================
    // ESTADOS DEL FORMULARIO DE LOGIN (CAMPOS DE ENTRADA)
    // Se utiliza mutableStateOf para vincular reactivamente los campos de texto en Compose.
    // 'private set' garantiza encapsulación: la UI lee las variables pero no las modifica directo.
    // =========================================================================

    /** Guarda el nombre de usuario ingresado en el campo de texto. */
    var usuarioInput by mutableStateOf("")
        private set

    /** Guarda la contraseña ingresada en el campo de texto. */
    var passwordInput by mutableStateOf("")
        private set

    // =========================================================================
    // ESTADOS DE SESIÓN Y ERRORES (UI STATE Y CONTROL DE FLUJO)
    // Se utiliza StateFlow para exponer un estado observable, seguro e inmutable a las pantallas.
    // =========================================================================

    /** Estado interno mutable que indica si el usuario tiene una sesión activa. */
    private val _isLoggedIn = MutableStateFlow(false)
    /** Estado público observable por la UI para controlar la navegación (Pantalla de Login vs App principal). */
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    /** Estado interno mutable para almacenar mensajes de error durante la autenticación. */
    private val _loginError = MutableStateFlow<String?>(null)
    /** Estado público observable por la UI para renderizar alertas de error en el formulario. */
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    // =========================================================================
    // FUNCIONES DE ACTUALIZACIÓN DE CAMPOS
    // =========================================================================

    /**
     * Actualiza el valor del usuario ingresado y limpia cualquier error de login activo.
     * @param nuevoUsuario Texto ingresado por el usuario.
     */
    fun onUsuarioChanged(nuevoUsuario: String) {
        usuarioInput = nuevoUsuario
        _loginError.value = null
    }

    /**
     * Actualiza el valor de la contraseña ingresada y limpia cualquier error de login activo.
     * @param nuevaPass Texto ingresado en el campo de contraseña.
     */
    fun onPasswordChanged(nuevaPass: String) {
        passwordInput = nuevaPass
        _loginError.value = null
    }

    // =========================================================================
    // LÓGICA DE AUTENTICACIÓN (INICIO Y CIERRE DE SESIÓN)
    // =========================================================================

    /**
     * Valida los campos e intenta iniciar sesión comparando con credenciales predefinidas.
     * @param onSuccess Callback opcional que se ejecuta tras una autenticación exitosa (ej. navegar al menú).
     */
    fun iniciarSesion(onSuccess: () -> Unit = {}) {
        // 1. Validar que no haya campos vacíos
        if (usuarioInput.isBlank() || passwordInput.isBlank()) {
            _loginError.value = "Por favor ingresa usuario y contraseña."
            return
        }

        // 2. Validación de credenciales fijas de prueba para funcionarios/administradores
        if (usuarioInput.trim().lowercase() == "admin" && passwordInput == "1234") {
            _isLoggedIn.value = true
            _loginError.value = null

            // Limpiar los campos de entrada tras el ingreso exitoso por seguridad
            usuarioInput = ""
            passwordInput = ""

            // Notificar éxito a la UI para realizar la navegación
            onSuccess()
        } else {
            // Mostrar error en caso de credenciales inválidas
            _loginError.value = "Credenciales incorrectas. Verifica tus datos."
        }
    }
    /**
     * Cierra la sesión activa del usuario y restablece las credenciales ingresadas.
     */
    fun cerrarSesion() {
        _isLoggedIn.value = false
        usuarioInput = ""
        passwordInput = ""
    }
}