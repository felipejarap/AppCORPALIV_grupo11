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

/**
 * ViewModel encargado de gestionar la lógica de negocio y el estado de las pantallas de Contacto
 * y Administración de Mensajes, conectando la interfaz de usuario (UI) con Room a través del repositorio.
 */
class ContactoViewModel(private val repository: ContactoRepository) : ViewModel() {

    // =========================================================================
    // ESTADOS DEL FORMULARIO DE CONTACTO (CAMPOS DE TEXTO)
    // Usan mutableStateOf para reactividad directa con Jetpack Compose.
    // 'private set' asegura la encapsulación: solo el ViewModel puede modificarlos.
    // =========================================================================

    /** Guarda el nombre ingresado por el usuario en el formulario. */
    var nombre by mutableStateOf("")
        private set

    /** Guarda el correo electrónico ingresado por el usuario. */
    var email by mutableStateOf("")
        private set

    /** Guarda el asunto opcional o título del mensaje. */
    var asunto by mutableStateOf("")
        private set

    /** Guarda el cuerpo principal del mensaje enviado por el usuario. */
    var mensaje by mutableStateOf("")
        private set

    // =========================================================================
    // ESTADO DE FILTRADO (LISTA DE MENSAJES DE ADMINISTRACIÓN)
    // =========================================================================

    /** Almacena el filtro activo seleccionado ("Todos", "Pendientes", "Contestados", "Rechazados"). */
    var filtroActual by mutableStateOf("Todos")
        private set

    // =========================================================================
    // ESTADOS DE FLUJO DE LA INTERFAZ (UI STATE Y CONTROL DE ERRORES)
    // Usan StateFlow para emitir cambios de estado asíncronos y seguros a la UI.
    // =========================================================================

    /** Estado interno que indica si el formulario fue enviado con éxito. */
    private val _isSubmitted = MutableStateFlow(false)
    /** Estado público observable por la UI para mostrar la tarjeta de confirmación. */
    val isSubmitted: StateFlow<Boolean> = _isSubmitted.asStateFlow()

    /** Estado interno para manejar mensajes de validación o error de la base de datos. */
    private val _errorMessage = MutableStateFlow<String?>(null)
    /** Estado público observable por la UI para renderizar alertas o errores en pantalla. */
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // =========================================================================
    // LECTURA DE DATOS EN TIEMPO REAL (ROOM DATA FLOW)
    // =========================================================================

    /**
     * Flujo continuo de mensajes desde la base de datos Room.
     * Se convierte en un StateFlow mediante [stateIn]:
     * - scope: Limitado a la vida útil de este ViewModel.
     * - started: 'WhileSubscribed(5000)' mantiene la lectura activa y espera 5s tras rotaciones de pantalla.
     * - initialValue: Lista vacía para evitar nulos mientras cargan los datos.
     */
    val listaContactos: StateFlow<List<ContactoEntity>> = repository.listaContactos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // =========================================================================
    // FUNCIONES DE ACTUALIZACIÓN DE CAMPOS DEL FORMULARIO
    // Cada vez que el usuario escribe, actualizan el estado y limpian los errores activos.
    // =========================================================================

    /** Actualiza el campo de nombre y limpia mensajes de error previos. */
    fun onNombreChanged(nuevoNombre: String) {
        nombre = nuevoNombre
        _errorMessage.value = null
    }

    /** Actualiza el campo de email y limpia mensajes de error previos. */
    fun onEmailChanged(nuevoEmail: String) {
        email = nuevoEmail
        _errorMessage.value = null
    }

    /** Actualiza el campo de asunto y limpia mensajes de error previos. */
    fun onAsuntoChanged(nuevoAsunto: String) {
        asunto = nuevoAsunto
        _errorMessage.value = null
    }

    /** Actualiza el campo de mensaje y limpia mensajes de error previos. */
    fun onMensajeChanged(nuevoMensaje: String) {
        mensaje = nuevoMensaje
        _errorMessage.value = null
    }

    // =========================================================================
    // GESTIÓN DE FILTROS EN LA LISTA
    // =========================================================================

    /**
     * Cambia el filtro actual para la lista de mensajes en la vista de administración.
     * @param nuevoFiltro Criterio de filtrado ("Todos", "Pendientes", "Contestados", "Rechazados").
     */
    fun cambiarFiltro(nuevoFiltro: String) {
        filtroActual = nuevoFiltro
    }

    // =========================================================================
    // CREACIÓN Y PERSISTENCIA (ENVÍO DEL FORMULARIO)
    // =========================================================================

    /**
     * Valida los datos ingresados y guarda el nuevo mensaje de contacto en Room de forma asíncrona.
     * @param onSuccess Callback opcional que se ejecuta tras completar el guardado con éxito.
     */
    fun enviarFormulario(onSuccess: () -> Unit = {}) {
        // 1. Validar que los campos obligatorios no estén vacíos
        if (nombre.isBlank() || email.isBlank() || mensaje.isBlank()) {
            _errorMessage.value = "Por favor completa los campos obligatorios (*)."
            return
        }

        // 2. Validar el formato de correo mediante la utilidad nativa de Android
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorMessage.value = "Por favor ingresa un correo electrónico válido."
            return
        }

        // Limpiar errores activos antes de proceder al guardado
        _errorMessage.value = null

        // 3. Insertar en Room dentro de una corrutina en segundo plano
        viewModelScope.launch {
            try {
                val nuevoContacto = ContactoEntity(
                    nombre = nombre,
                    email = email,
                    asunto = asunto,
                    mensaje = mensaje
                    // 'estado' toma automáticamente "Pendiente" por defecto en la data class ContactoEntity
                )
                repository.guardarContacto(nuevoContacto)

                // Notificar éxito a la interfaz
                _isSubmitted.value = true
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Error al guardar localmente: ${e.message}"
            }
        }
    }

    // =========================================================================
    // ACTUALIZACIÓN DE REGISTROS (UPDATE)
    // =========================================================================

    /**
     * Actualiza un registro de contacto existente en la base de datos Room.
     * @param contacto Objeto [ContactoEntity] con los datos modificados.
     */
    fun actualizarContacto(contacto: ContactoEntity) {
        viewModelScope.launch {
            try {
                repository.actualizarContacto(contacto)
            } catch (e: Exception) {
                _errorMessage.value = "Error al actualizar: ${e.message}"
            }
        }
    }

    /**
     * Cambia el estado de respuesta de un mensaje ("Pendiente" -> "Contestado" o "Rechazado").
     * Utiliza la función `.copy()` para preservar la inmutabilidad de la entidad.
     */
    fun cambiarEstadoContacto(contacto: ContactoEntity, nuevoEstado: String) {
        actualizarContacto(contacto.copy(estado = nuevoEstado))
    }

    // =========================================================================
    // ELIMINACIÓN DE REGISTROS (DELETE)
    // =========================================================================

    /**
     * Elimina de forma permanente un registro de mensaje de la base de datos.
     * @param contacto Registro [ContactoEntity] a eliminar.
     */
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

    /**
     * Limpia los campos de texto y restablece los estados de error y confirmación
     * para permitir ingresar un nuevo mensaje desde cero.
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