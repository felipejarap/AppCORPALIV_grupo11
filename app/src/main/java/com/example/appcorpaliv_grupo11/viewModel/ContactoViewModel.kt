package com.example.appcorpaliv_grupo11.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import com.example.appcorpaliv_grupo11.repository.ContactoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de gestionar la lógica de negocio y el estado de las pantallas de Contacto
 * y Administración de Mensajes, conectando la interfaz de usuario (UI) con Room a través del repositorio.
 */
class ContactoViewModel(private val repository: ContactoRepository) : ViewModel() {

    // =========================================================================
    // ESTADOS DEL FORMULARIO DE CONTACTO (CAMPOS DE TEXTO Y DROPDOWN)
    // =========================================================================

    /** Guarda el nombre ingresado por el usuario. */
    var nombre by mutableStateOf("")
        private set

    /** Guarda el correo electrónico ingresado por el usuario. */
    var email by mutableStateOf("")
        private set

    /** Guarda el número de teléfono móvil de 9 dígitos. */
    var telefono by mutableStateOf("")
        private set

    /** Guarda la opción seleccionada de asunto. */
    var asunto by mutableStateOf("")
        private set

    /** Guarda el cuerpo principal del mensaje enviado por el usuario. */
    var mensaje by mutableStateOf("")
        private set

    // =========================================================================
    // OPCIONES PREDEFINIDAS PARA EL COMBOBOX / DROPDOWN DE ASUNTOS
    // =========================================================================

    val opcionesAsunto = listOf(
        "Programa de Intermediación Laboral",
        "Programa Taller Sociolaboral",
        "Quiero colaborar",
        "Ley de Inclusión Laboral"
    )

    // =========================================================================
    // ERRORES DE VALIDACIÓN INDIVIDUALES POR CAMPO
    // =========================================================================

    var nombreError by mutableStateOf<String?>(null)
        private set

    var emailError by mutableStateOf<String?>(null)
        private set

    var telefonoError by mutableStateOf<String?>(null)
        private set

    var asuntoError by mutableStateOf<String?>(null)
        private set

    var mensajeError by mutableStateOf<String?>(null)
        private set

    // =========================================================================
    // ESTADO DE FILTRADO (LISTA DE MENSAJES DE ADMINISTRACIÓN)
    // =========================================================================

    /** Almacena el filtro activo seleccionado ("Todos", "Pendientes", "Contestados", "Rechazados"). */
    var filtroActual by mutableStateOf("Todos")
        private set

    // =========================================================================
    // ESTADOS DE FLUJO DE LA INTERFAZ (UI STATE, LOADING Y ERROR GENERAL DE BASE DE DATOS)
    // =========================================================================

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSubmitted = MutableStateFlow(false)
    val isSubmitted: StateFlow<Boolean> = _isSubmitted.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // =========================================================================
    // LECTURA DE DATOS EN TIEMPO REAL (ROOM DATA FLOW)
    // =========================================================================

    val listaContactos: StateFlow<List<ContactoEntity>> = repository.listaContactos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // =========================================================================
    // FUNCIONES DE ACTUALIZACIÓN DE CAMPOS DEL FORMULARIO
    // =========================================================================

    fun onNombreChanged(nuevoNombre: String) {
        nombre = nuevoNombre
        nombreError = null
    }

    fun onEmailChanged(nuevoEmail: String) {
        email = nuevoEmail
        emailError = null
    }

    /**
     * Filtra la entrada para permitir únicamente dígitos numéricos y un máximo de 9 caracteres.
     */
    fun onTelefonoChanged(nuevoTelefono: String) {
        val filtrado = nuevoTelefono.filter { it.isDigit() }.take(9)
        telefono = filtrado
        telefonoError = null
    }

    fun onAsuntoChanged(nuevoAsunto: String) {
        asunto = nuevoAsunto
        asuntoError = null
    }

    fun onMensajeChanged(nuevoMensaje: String) {
        mensaje = nuevoMensaje
        mensajeError = null
    }

    fun cambiarFiltro(nuevoFiltro: String) {
        filtroActual = nuevoFiltro
    }

    // =========================================================================
    // VALIDACIONES INDIVIDUALES DEL FORMULARIO
    // =========================================================================

    private fun validarCampos(): Boolean {
        var esValido = true

        if (nombre.isBlank()) {
            nombreError = "El nombre es obligatorio *"
            esValido = false
        }

        if (email.isBlank()) {
            emailError = "El correo electrónico es obligatorio *"
            esValido = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Ingresa un correo electrónico válido."
            esValido = false
        }

        if (telefono.isBlank()) {
            telefonoError = "El teléfono es obligatorio *"
            esValido = false
        } else if (telefono.length != 9 || !telefono.startsWith("9")) {
            telefonoError = "Debe ingresar 9 dígitos comenzando con 9 (Ej: 912345678)."
            esValido = false
        }

        if (asunto.isBlank()) {
            asuntoError = "Por favor selecciona un asunto *"
            esValido = false
        }

        if (mensaje.isBlank()) {
            mensajeError = "El mensaje no puede estar vacío *"
            esValido = false
        }

        return esValido
    }

    // =========================================================================
    // CREACIÓN Y PERSISTENCIA (ENVÍO DEL FORMULARIO CON ANIMACIÓN DE CARGA)
    // =========================================================================

    fun enviarFormulario(onSuccess: () -> Unit = {}) {
        if (!validarCampos()) return

        _errorMessage.value = null

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Simula el tiempo de procesamiento/envío de datos (1.5 segundos)
                delay(1500)

                val nuevoContacto = ContactoEntity(
                    nombre = nombre,
                    email = email,
                    asunto = "$asunto | Tel: +56$telefono",
                    mensaje = mensaje
                )
                repository.guardarContacto(nuevoContacto)

                _isSubmitted.value = true
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Error al guardar localmente: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // =========================================================================
    // ACTUALIZACIÓN DE REGISTROS (UPDATE) Y ELIMINACIÓN
    // =========================================================================

    fun actualizarContacto(contacto: ContactoEntity) {
        viewModelScope.launch {
            try {
                repository.actualizarContacto(contacto)
            } catch (e: Exception) {
                _errorMessage.value = "Error al actualizar: ${e.message}"
            }
        }
    }

    fun cambiarEstadoContacto(contacto: ContactoEntity, nuevoEstado: String) {
        actualizarContacto(contacto.copy(estado = nuevoEstado))
    }

    fun eliminarContacto(contacto: ContactoEntity) {
        viewModelScope.launch {
            try {
                repository.eliminarContacto(contacto)
            } catch (e: Exception) {
                _errorMessage.value = "Error al eliminar: ${e.message}"
            }
        }
    }

    // =========================================================================
    // RESTABLECIMIENTO DEL FORMULARIO
    // =========================================================================

    fun reiniciarFormulario() {
        nombre = ""
        email = ""
        telefono = ""
        asunto = ""
        mensaje = ""

        nombreError = null
        emailError = null
        telefonoError = null
        asuntoError = null
        mensajeError = null

        _isLoading.value = false
        _isSubmitted.value = false
        _errorMessage.value = null
    }
}