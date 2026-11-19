package com.example.appcorpaliv_grupo11.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcorpaliv_grupo11.network.ExternalIndicadoresClient
import com.example.appcorpaliv_grupo11.network.IndicadoresResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IndicadoresViewModel : ViewModel() {

    private val _indicadores = MutableStateFlow<IndicadoresResponse?>(null)
    val indicadores: StateFlow<IndicadoresResponse?> = _indicadores.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("IndicadoresViewModel", "Error controlado en corrutina de indicadores", throwable)
        _isLoading.value = false
        _errorMessage.value = "Información económica no disponible en este momento."
    }

    init {
        cargarIndicadores()
    }

    fun cargarIndicadores() {
        viewModelScope.launch(exceptionHandler) {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // 1. Intento principal: mindicador.cl
                val primaryResponse = runCatching {
                    ExternalIndicadoresClient.api.obtenerIndicadoresDiarios()
                }.getOrNull()

                if (primaryResponse != null && primaryResponse.isSuccessful && primaryResponse.body() != null) {
                    _indicadores.value = primaryResponse.body()
                    return@launch
                }

                Log.w(
                    "IndicadoresViewModel",
                    "mindicador.cl no disponible (HTTP ${primaryResponse?.code() ?: "timeout"}), consultando respaldo..."
                )

                // 2. Respaldo automático: findic.cl (misma estructura JSON)
                val fallbackResponse = ExternalIndicadoresClient.api.obtenerIndicadoresDesdeUrl(
                    ExternalIndicadoresClient.FALLBACK_URL
                )

                if (fallbackResponse.isSuccessful && fallbackResponse.body() != null) {
                    _indicadores.value = fallbackResponse.body()
                } else {
                    Log.w(
                        "IndicadoresViewModel",
                        "Respuesta no exitosa en respaldo de indicadores: HTTP ${fallbackResponse.code()}"
                    )
                    _errorMessage.value = "Información económica no disponible en este momento."
                }
            } catch (e: Exception) {
                Log.e("IndicadoresViewModel", "Error de red al obtener indicadores", e)
                _errorMessage.value = "No fue posible actualizar los indicadores. Revisa tu conexión."
            } finally {
                _isLoading.value = false
            }
        }
    }
}