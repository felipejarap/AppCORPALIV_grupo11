package com.example.appcorpaliv_grupo11.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {

    var usuarioInput by mutableStateOf("")
        private set

    var passwordInput by mutableStateOf("")
        private set

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    fun onUsuarioChanged(nuevoUsuario: String) {
        usuarioInput = nuevoUsuario
        _loginError.value = null
    }

    fun onPasswordChanged(nuevaPass: String) {
        passwordInput = nuevaPass
        _loginError.value = null
    }

    fun iniciarSesion(onSuccess: () -> Unit = {}) {
        if (usuarioInput.isBlank() || passwordInput.isBlank()) {
            _loginError.value = "Por favor ingresa usuario y contraseña."
            return
        }

        // Credenciales fijas de prueba para funcionarios
        if (usuarioInput.trim().lowercase() == "admin" && passwordInput == "1234") {
            _isLoggedIn.value = true
            _loginError.value = null
            usuarioInput = ""
            passwordInput = ""
            onSuccess()
        } else {
            _loginError.value = "Credenciales incorrectas. Verifica tus datos."
        }
    }
    fun cerrarSesion() {
        _isLoggedIn.value = false
        usuarioInput = ""
        passwordInput = ""
    }
}