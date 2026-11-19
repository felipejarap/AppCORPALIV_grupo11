package com.example.appcorpaliv_grupo11.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import com.example.appcorpaliv_grupo11.repository.ContactoRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * VIEWMODEL DE CONTACTO Y ADMINISTRACIÓN DE MENSAJES
 *
 * Gestiona la lógica de negocio y el estado reactivo para las pantallas de Contacto y Lista de Contactos.
 * Actúa como intermediario entre las vistas Compose y la API REST (Spring Boot + MySQL) a través de ContactoRepository.
 *
 * @property repository Instancia del repositorio para realizar operaciones CRUD contra el microservicio.
 */
class ContactoViewModel(private val repository: ContactoRepository) : ViewModel() {

    // =========================================================================
    // ESTADOS DEL FORMULARIO DE CONTACTO (CAMPOS DE TEXTO Y DROPDOWN)
    // =========================================================================

    var nombre by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var telefono by mutableStateOf("")
        private set

    var asunto by mutableStateOf("")
        private set

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

    var filtroActual by mutableStateOf("Todos")
        private set

    // =========================================================================
    // ESTADOS DE FLUJO DE LA INTERFAZ (UI STATE, LOADING Y ERROR GENERAL)
    // =========================================================================

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSubmitted = MutableStateFlow(false)
    val isSubmitted: StateFlow<Boolean> = _isSubmitted.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    /**
     * Escudo global contra cierres inesperados (crashes) en cualquier corrutina del ViewModel.
     * Mantiene los detalles técnicos únicamente en el Log interno.
     */
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ContactoViewModel", "Error controlado en corrutina", throwable)
        _isLoading.value = false
        _errorMessage.value = "El servicio no está disponible en este momento. Intenta nuevamente."
    }

    // =========================================================================
    // LECTURA DE DATOS EN TIEMPO REAL (STATEFLOW DESDE REPOSITORY)
    // =========================================================================

    val listaContactos: StateFlow<List<ContactoEntity>> = repository.listaContactos
        .catch { throwable ->
            Log.e("ContactoViewModel", "Error en flujo de contactos", throwable)
            _errorMessage.value = "No pudimos cargar los mensajes. Revisa tu conexión e intenta nuevamente."
            emit(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // =========================================================================
    // INICIALIZACIÓN Y SINCRONIZACIÓN CON BACKEND
    // =========================================================================

    init {
        sincronizarContactos()
    }

    /** Descarga los contactos desde el backend. */
    fun sincronizarContactos() {
        viewModelScope.launch(exceptionHandler) {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                repository.sincronizarDesdeServidor()
            } catch (e: Exception) {
                Log.w("ContactoViewModel", "Error de conexión al sincronizar", e)
                _errorMessage.value = "Servicio temporalmente no disponible. Verifica tu conexión e intenta nuevamente."
            } finally {
                _isLoading.value = false
            }
        }
    }

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

    fun limpiarError() {
        _errorMessage.value = null
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
    // CREACIÓN Y PERSISTENCIA (ENVÍO DEL FORMULARIO AL MICROSERVICIO)
    // =========================================================================

    fun enviarFormulario(onSuccess: () -> Unit = {}) {
        if (!validarCampos()) return

        _errorMessage.value = null

        viewModelScope.launch(exceptionHandler) {
            _isLoading.value = true
            try {
                val nuevoContacto = ContactoEntity(
                    nombre = nombre.trim(),
                    email = email.trim(),
                    telefono = telefono.trim(),
                    asunto = asunto.trim(),
                    mensaje = mensaje.trim()
                )
                repository.guardarContacto(nuevoContacto)

                _isSubmitted.value = true
                onSuccess()
            } catch (e: Exception) {
                Log.e("ContactoViewModel", "Error al guardar contacto", e)
                _errorMessage.value = "No pudimos enviar tu mensaje en este momento. Intenta más tarde."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // =========================================================================
    // CONSULTA POR ID, ACTUALIZACIÓN (UPDATE) Y ELIMINACIÓN (DELETE)
    // =========================================================================

    fun buscarPorId(id: Int): ContactoEntity? {
        return try {
            repository.obtenerPorId(id)
        } catch (e: Exception) {
            Log.e("ContactoViewModel", "Error al buscar contacto", e)
            _errorMessage.value = "No fue posible consultar el registro solicitado."
            null
        }
    }

    fun actualizarContacto(contacto: ContactoEntity) {
        _errorMessage.value = null
        viewModelScope.launch(exceptionHandler) {
            try {
                repository.actualizarContacto(contacto)
            } catch (e: Exception) {
                Log.e("ContactoViewModel", "Error al actualizar contacto", e)
                _errorMessage.value = "No se pudo actualizar el estado en este momento. Intenta nuevamente."
            }
        }
    }

    fun cambiarEstadoContacto(contacto: ContactoEntity, nuevoEstado: String) {
        actualizarContacto(contacto.copy(estado = nuevoEstado))
    }

    fun eliminarContacto(contacto: ContactoEntity) {
        _errorMessage.value = null
        viewModelScope.launch(exceptionHandler) {
            try {
                repository.eliminarContacto(contacto)
            } catch (e: Exception) {
                Log.e("ContactoViewModel", "Error al eliminar contacto", e)
                _errorMessage.value = "No se pudo eliminar el mensaje en este momento. Intenta nuevamente."
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