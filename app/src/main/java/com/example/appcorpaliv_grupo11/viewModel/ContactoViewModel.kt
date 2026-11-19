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
import kotlin.time.Duration.Companion.milliseconds

/**
 * VIEWMODEL DE CONTACTO Y ADMINISTRACIÓN DE MENSAJES
 *
 * Gestiona la lógica de negocio y el estado reactivo para las pantallas de Contacto y Lista de Contactos.
 * Actúa como intermediario entre las vistas Compose y la base de datos Room a través de ContactoRepository.
 *
 * @property repository Instancia del repositorio para realizar operaciones CRUD sobre la base de datos.
 */
class ContactoViewModel(private val repository: ContactoRepository) : ViewModel() {

    // =========================================================================
    // ESTADOS DEL FORMULARIO DE CONTACTO (CAMPOS DE TEXTO Y DROPDOWN)
    // =========================================================================

    /** Guarda el nombre ingresado por el usuario con private set para evitar modificaciones directas externas. */
    var nombre by mutableStateOf("")
        private set

    /** Guarda el correo electrónico ingresado por el usuario. */
    var email by mutableStateOf("")
        private set

    /** Guarda el número de teléfono móvil de 9 dígitos. */
    var telefono by mutableStateOf("")
        private set

    /** Guarda la opción seleccionada del menú desplegable de asunto. */
    var asunto by mutableStateOf("")
        private set

    /** Guarda el cuerpo principal del mensaje enviado por el usuario. */
    var mensaje by mutableStateOf("")
        private set

    // =========================================================================
    // OPCIONES PREDEFINIDAS PARA EL COMBOBOX / DROPDOWN DE ASUNTOS
    // =========================================================================

    /** Lista inmutable con las categorías temáticas predefinidas para el campo de asunto. */
    val opcionesAsunto = listOf(
        "Programa de Intermediación Laboral",
        "Programa Taller Sociolaboral",
        "Quiero colaborar",
        "Ley de Inclusión Laboral"
    )

    // =========================================================================
    // ERRORES DE VALIDACIÓN INDIVIDUALES POR CAMPO
    // =========================================================================

    /** Mensaje de error para la validación del campo nombre (null si es válido). */
    var nombreError by mutableStateOf<String?>(null)
        private set

    /** Mensaje de error para la validación del campo email (null si es válido). */
    var emailError by mutableStateOf<String?>(null)
        private set

    /** Mensaje de error para la validación del campo teléfono (null si es válido). */
    var telefonoError by mutableStateOf<String?>(null)
        private set

    /** Mensaje de error para la validación del asunto (null si es válido). */
    var asuntoError by mutableStateOf<String?>(null)
        private set

    /** Mensaje de error para la validación del cuerpo del mensaje (null si es válido). */
    var mensajeError by mutableStateOf<String?>(null)
        private set

    // =========================================================================
    // ESTADO DE FILTRADO (LISTA DE MENSAJES DE ADMINISTRACIÓN)
    // =========================================================================

    /** Almacena el criterio de filtrado activo en el panel ("Todos", "Pendientes", "Contestados", "Rechazados"). */
    var filtroActual by mutableStateOf("Todos")
        private set

    // =========================================================================
    // ESTADOS DE FLUJO DE LA INTERFAZ (UI STATE, LOADING Y ERROR GENERAL DE BASE DE DATOS)
    // =========================================================================

    /** Estado interno mutable para controlar el indicador de carga en la UI (ProgressBar). */
    private val _isLoading = MutableStateFlow(false)
    /** Estado público observable de solo lectura para la animación de carga. */
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    /** Estado interno mutable que indica si el formulario fue enviado correctamente. */
    private val _isSubmitted = MutableStateFlow(false)
    /** Estado público observable de solo lectura que activa la tarjeta de confirmación de éxito. */
    val isSubmitted: StateFlow<Boolean> = _isSubmitted.asStateFlow()

    /** Estado interno mutable para capturar excepciones o errores al interactuar con Room. */
    private val _errorMessage = MutableStateFlow<String?>(null)
    /** Estado público observable de solo lectura para desplegar alertas de error general. */
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // =========================================================================
    // LECTURA DE DATOS EN TIEMPO REAL (ROOM DATA FLOW)
    // =========================================================================

    /**
     * Convierte el Flow continuo de la base de datos a un StateFlow seguro para la UI.
     *
     * stateIn: Transforma un Flow frío en un StateFlow caliente observable por Compose.
     * SharingStarted.WhileSubscribed(5000): Mantiene el flujo activo mientras exista al menos
     * un suscriptor (la UI). Mantiene la conexión por 5 segundos adicionales tras pausar la vista.
     */
    val listaContactos: StateFlow<List<ContactoEntity>> = repository.listaContactos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // =========================================================================
    // FUNCIONES DE ACTUALIZACIÓN DE CAMPOS DEL FORMULARIO
    // =========================================================================

    /** Actualiza el nombre e Invalida/Limpia su error previo. */
    fun onNombreChanged(nuevoNombre: String) {
        nombre = nuevoNombre
        nombreError = null
    }

    /** Actualiza el correo electrónico e Invalida/Limpia su error previo. */
    fun onEmailChanged(nuevoEmail: String) {
        email = nuevoEmail
        emailError = null
    }

    /**
     * Filtra la entrada para permitir únicamente dígitos numéricos y un máximo de 9 caracteres.
     *
     * @param nuevoTelefono Cadena recibida del input de texto.
     */
    fun onTelefonoChanged(nuevoTelefono: String) {
        val filtrado = nuevoTelefono.filter { it.isDigit() }.take(9)
        telefono = filtrado
        telefonoError = null
    }

    /** Actualiza la opción de asunto e Invalida/Limpia su error previo. */
    fun onAsuntoChanged(nuevoAsunto: String) {
        asunto = nuevoAsunto
        asuntoError = null
    }

    /** Actualiza el texto del mensaje e Invalida/Limpia su error previo. */
    fun onMensajeChanged(nuevoMensaje: String) {
        mensaje = nuevoMensaje
        mensajeError = null
    }

    /** Modifica el filtro de búsqueda para la lista administrativa de mensajes. */
    fun cambiarFiltro(nuevoFiltro: String) {
        filtroActual = nuevoFiltro
    }

    // =========================================================================
    // VALIDACIONES INDIVIDUALES DEL FORMULARIO
    // =========================================================================

    /**
     * Ejecuta las reglas de negocio sobre cada campo del formulario.
     *
     * @return true si todos los campos cumplen los requisitos de formato y obligatoriedad, false en caso contrario.
     */
    private fun validarCampos(): Boolean {
        var esValido = true

        // Validar Nombre Obligatorio
        if (nombre.isBlank()) {
            nombreError = "El nombre es obligatorio *"
            esValido = false
        }

        // Validar Email con Expresión Regular estándar Android
        if (email.isBlank()) {
            emailError = "El correo electrónico es obligatorio *"
            esValido = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Ingresa un correo electrónico válido."
            esValido = false
        }

        // Validar Formato Chileno de Celular (9 dígitos iniciando con '9')
        if (telefono.isBlank()) {
            telefonoError = "El teléfono es obligatorio *"
            esValido = false
        } else if (telefono.length != 9 || !telefono.startsWith("9")) {
            telefonoError = "Debe ingresar 9 dígitos comenzando con 9 (Ej: 912345678)."
            esValido = false
        }

        // Validar Selección de Asunto
        if (asunto.isBlank()) {
            asuntoError = "Por favor selecciona un asunto *"
            esValido = false
        }

        // Validar Mensaje No Vacío
        if (mensaje.isBlank()) {
            mensajeError = "El mensaje no puede estar vacío *"
            esValido = false
        }

        return esValido
    }

    // =========================================================================
    // CREACIÓN Y PERSISTENCIA (ENVÍO DEL FORMULARIO CON ANIMACIÓN DE CARGA)
    // =========================================================================

    /**
     * Valida, simula el tiempo de red y guarda la información en la base de datos Room.
     *
     * @param onSuccess Callback ejecutado al completar el guardado.
     */
    fun enviarFormulario(onSuccess: () -> Unit = {}) {
        if (!validarCampos()) return

        _errorMessage.value = null

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Se utiliza la API de tipo explícito (1500.milliseconds) para eliminar la advertencia
                delay(1500.milliseconds)

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

    /** Actualiza un registro de contacto en Room mediante corrutinas. */
    fun actualizarContacto(contacto: ContactoEntity) {
        viewModelScope.launch {
            try {
                repository.actualizarContacto(contacto)
            } catch (e: Exception) {
                _errorMessage.value = "Error al actualizar: ${e.message}"
            }
        }
    }

    /** Cambia el estado de un mensaje ("Pendiente", "Contestado", "Rechazado") creando una copia con .copy(). */
    fun cambiarEstadoContacto(contacto: ContactoEntity, nuevoEstado: String) {
        actualizarContacto(contacto.copy(estado = nuevoEstado))
    }

    /** Elimina permanentemente un registro de la base de datos dentro de viewModelScope. */
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

    /** Restablece todos los campos de texto, mensajes de error y estados del flujo a sus valores iniciales. */
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