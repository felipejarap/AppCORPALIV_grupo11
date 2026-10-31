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
 * VIEWMODEL GLOBAL DE LA APLICACIÓN (MAIN VIEWMODEL)
 *
 * Gestiona configuraciones transversales a todo el ciclo de vida de la aplicación,
 * tales como la preferencia del tema visual (Modo Oscuro / Claro) a través de [EstadoDataStore]
 * y la emisión de eventos de navegación desacoplados hacia el marco de Compose.
 *
 * Hereda de [AndroidViewModel] para acceder de forma segura al contexto global (`application`).
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // =========================================================================
    // 1. PERSISTENCIA Y ESTADO DEL TEMA VISUAL (DataStore)
    // =========================================================================

    /** Instancia del gestor de almacenamiento de preferencias persistentes [EstadoDataStore] en disco. */
    private val dataStore = EstadoDataStore(application)

    /**
     * Estado observable del tema activo (Modo Oscuro habilitado / deshabilitado).
     *
     * Se convierte desde el Flow continuo de DataStore mediante [stateIn]:
     * - scope: `viewModelScope` limita la recolección a la vida útil del ViewModel.
     * - started: `SharingStarted.WhileSubscribed(5000)` mantiene la conexión activa y tolera rotaciones de pantalla.
     * - initialValue: `false` (Modo Claro por defecto) mientras lee el valor persistido en disco.
     */
    val isDarkTheme: StateFlow<Boolean> = dataStore.isDarkMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    /**
     * Alterna y guarda de forma persistente el estado del modo oscuro en DataStore.
     * Invierte el valor actual de [isDarkTheme] ejecutando la escritura en una corrutina en segundo plano.
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

    /**
     * Flujo compartido interno [MutableSharedFlow] para emitir eventos de navegación únicos ("One-shot events").
     * A diferencia de StateFlow, no conserva un estado persistente en memoria, evitando navegaciones duplicadas.
     */
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()

    /** Flujo público de solo lectura observable por el NavHost principal para ejecutar las transiciones de pantalla. */
    val navigationEvents: SharedFlow<NavigationEvent> = _navigationEvents.asSharedFlow()

    /**
     * Solicita la navegación hacia una pantalla específica enviando un evento [NavigationEvent.NavigateTo].
     *
     * @param screen Ruta o pantalla de destino definida en la clase sellada [Screen].
     */
    fun navigateTo(screen: Screen) {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateTo(route = screen))
        }
    }

    /**
     * Solicita retroceder a la pantalla anterior eliminando la actual de la pila de navegación (Backstack).
     */
    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.PopBackStack)
        }
    }

    /**
     * Solicita realizar un desplazamiento hacia arriba en la jerarquía visual de navegación.
     */
    fun navigateUp() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateUp)
        }
    }
}