package com.example.appcorpaliv_grupo11.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import com.example.appcorpaliv_grupo11.repository.ContactoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactoViewModel(private val repository: ContactoRepository) : ViewModel() {

    // --- ESTADOS DEL FORMULARIO (CAMPOS DE TEXTO) ---
    var nombre by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var asunto by mutableStateOf("")
        private set

    var mensaje by mutableStateOf("")
        private set

    // --- ESTADOS DE FLUJO (UI STATE Y ERRORES) ---
    private val _isSubmitted = MutableStateFlow(false)
    val isSubmitted: StateFlow<Boolean> = _isSubmitted.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // --- LEER (READ) ---
    // Flow de Room convertido en StateFlow observable por la UI en tiempo real
    val listaContactos: StateFlow<List<ContactoEntity>> = repository.listaContactos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // --- ACTUALIZADORES DE CAMPOS ---
    fun onNombreChanged(nuevoNombre: String) {
        nombre = nuevoNombre
        _errorMessage.value = null
    }

    fun onEmailChanged(nuevoEmail: String) {
        email = nuevoEmail
        _errorMessage.value = null
    }

    fun onAsuntoChanged(nuevoAsunto: String) {
        asunto = nuevoAsunto
        _errorMessage.value = null
    }

    fun onMensajeChanged(nuevoMensaje: String) {
        mensaje = nuevoMensaje
        _errorMessage.value = null
    }

    // --- CREAR (CREATE) CON VALIDACIÓN ---
    fun enviarFormulario(onSuccess: () -> Unit = {}) {
        if (nombre.isBlank() || email.isBlank() || mensaje.isBlank()) {
            _errorMessage.value = "Por favor completa los campos obligatorios (*)."
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorMessage.value = "Por favor ingresa un correo electrónico válido."
            return
        }

        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val nuevoContacto = ContactoEntity(
                    nombre = nombre,
                    email = email,
                    asunto = asunto,
                    mensaje = mensaje
                )
                repository.guardarContacto(nuevoContacto)
                _isSubmitted.value = true
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Error al guardar localmente: ${e.message}"
            }
        }
    }

    // --- ACTUALIZAR (UPDATE) ---
    fun actualizarContacto(contacto: ContactoEntity) {
        viewModelScope.launch {
            try {
                repository.actualizarContacto(contacto)
            } catch (e: Exception) {
                _errorMessage.value = "Error al actualizar: ${e.message}"
            }
        }
    }

    // --- ELIMINAR (DELETE) ---
    fun eliminarContacto(contacto: ContactoEntity) {
        viewModelScope.launch {
            try {
                repository.eliminarContacto(contacto)
            } catch (e: Exception) {
                _errorMessage.value = "Error al eliminar: ${e.message}"
            }
        }
    }

    // --- REINICIAR FORMULARIO ---
    fun reiniciarFormulario() {
        nombre = ""
        email = ""
        asunto = ""
        mensaje = ""
        _isSubmitted.value = false
        _errorMessage.value = null
    }
}