package com.example.appcorpaliv_grupo11.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcorpaliv_grupo11.data.EstadoDataStore
import com.example.appcorpaliv_grupo11.navigation.NavigationEvent
import com.example.appcorpaliv_grupo11.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel global de la aplicación que gestiona configuraciones transversales,
 * como la preferencia del tema visual (Modo Oscuro/Claro) mediante DataStore
 * y los eventos de navegación desacoplados entre vistas.
 *
 * Hereda de [AndroidViewModel] para disponer del contexto global de la aplicación.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // =========================================================================
    // 1. PERSISTENCIA Y ESTADO DEL TEMA VISUAL (DataStore)
    // =========================================================================

    /** Instancia del gestor de almacenamiento de preferencias persistentes en disco. */
    private val dataStore = EstadoDataStore(application)

    /**
     * Estado observable del tema activo (Modo Oscuro habilitado / deshabilitado).
     * Se convierte desde el Flow de DataStore mediante [stateIn]:
     * - scope: Limitado a la vida útil del ViewModel.
     * - started: 'WhileSubscribed(5000)' mantiene la recolección activa y tolera rotaciones de pantalla.
     * - initialValue: 'false' (Modo Claro) por defecto mientras lee el estado guardado.
     */
    val isDarkTheme: StateFlow<Boolean> = dataStore.isDarkMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    /**
     * Alterna y guarda de forma persistente el estado del modo oscuro en DataStore.
     * Invierte el valor actual de [isDarkTheme] dentro de una corrutina en segundo plano.
     */
    fun toggleTheme() {
        viewModelScope.launch {
            dataStore.saveDarkModeState(!isDarkTheme.value)
        }
    }

    // =========================================================================
    // 2. NAVEGACIÓN DESACOPLADA (SharedFlow)
    // Permite que la lógica de presentación solicite cambios de pantalla sin acoplar
    // directamente el NavController dentro de las vistas.
    // =========================================================================

    /** Flujo compartido interno para emitir eventos de navegación únicos ("One-shot events"). */
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()

    /** Flujo público observable por el NavHost principal para reaccionar a las solicitudes de navegación. */
    val navigationEvents: SharedFlow<NavigationEvent> = _navigationEvents.asSharedFlow()

    /**
     * Solicita navegar hacia una pantalla especificada.
     * @param screen Ruta o pantalla de destino definida en la sellada [Screen].
     */
    fun navigateTo(screen: Screen) {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateTo(route = screen))
        }
    }

    /**
     * Solicita retroceder a la pantalla anterior eliminando la actual de la pila de navegación.
     */
    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.PopBackStack)
        }
    }

    /**
     * Solicita realizar un retroceso hacia arriba en la jerarquía de navegación.
     */
    fun navigateUp() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateUp)
        }
    }
}