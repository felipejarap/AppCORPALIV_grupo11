package com.example.appcorpaliv_grupo11.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcorpaliv_grupo11.data.EstadoDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// ViewModel extendido desde AndroidViewModel porque usaremos contexto[cite: 5]
class EstadoViewModel(application: Application) : AndroidViewModel(application) {

    // DataStore creado con contexto de aplicación[cite: 5]
    private val estadoDataStore = EstadoDataStore(application)

    // Estado que representa si está activado o no (observable)[cite: 5]
    private val _activo = MutableStateFlow<Boolean?>(value = null)
    val activo: StateFlow<Boolean?> = _activo

    // Estado para mostrar u ocultar el mensaje animado[cite: 5]
    private val _mostrarMensaje = MutableStateFlow(value = false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje

    init {
        // Al iniciar el ViewModel, cargamos el estado desde DataStore[cite: 5]
        cargarEstado()
    }

    fun cargarEstado() {
        viewModelScope.launch {
            // Simula demora para mostrar Loader (opcional)[cite: 5]
            delay(timeMillis = 1500)
            _activo.value = estadoDataStore.obtenerEstado().first() ?: false
        }
    }

    fun alternarEstado() {
        viewModelScope.launch {
            // Alternamos el valor actual[cite: 5]
            val nuevoValor = !(_activo.value ?: false)

            // Guardamos en DataStore[cite: 5]
            estadoDataStore.guardarEstado(nuevoValor)

            // Actualizamos el flujo[cite: 5]
            _activo.value = nuevoValor

            // Mostramos el mensaje visual animado[cite: 5]
            _mostrarMensaje.value = true
            delay(timeMillis = 2000) // Ocultamos después de 2 segundos[cite: 5]
            _mostrarMensaje.value = false
        }
    }
}