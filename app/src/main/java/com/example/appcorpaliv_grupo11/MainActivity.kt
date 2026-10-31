package com.example.appcorpaliv_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcorpaliv_grupo11.navigation.AppNavigation
import com.example.appcorpaliv_grupo11.ui.components.NoInternetScreen
import com.example.appcorpaliv_grupo11.ui.theme.AppCORPALIV_grupo11Theme
import com.example.appcorpaliv_grupo11.ui.utils.isInternetAvailable
import com.example.appcorpaliv_grupo11.viewModel.MainViewModel

/**
 * ACTIVIDAD PRINCIPAL Y PUNTO DE ENTRADA (SINGLE ACTIVITY ARCHITECTURE)
 *
 * Es la única Actividad de Android en el proyecto (`ComponentActivity`). Actúa como el contenedor host
 * para la interfaz declarativa construida con Jetpack Compose.
 * Inicializa el tema global (Modo Oscuro/Claro), gestiona la inyección inicial del `MainViewModel`
 * y evalúa la disponibilidad de red para alternar entre el flujo de navegación principal o la pantalla offline.
 */
class MainActivity : ComponentActivity() {

    /**
     * Punto de inicio del ciclo de vida de la Actividad.
     *
     * @param savedInstanceState Estado previamente guardado de la Actividad en caso de recreación por el sistema.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Asigna el bloque de composición declarativa de Compose como vista raíz de la pantalla
        setContent {
            // =========================================================================
            // INICIALIZACIÓN DE VIEWMODEL Y ESTADOS GLOBALES
            // =========================================================================

            /** Instancia del ViewModel global para la gestión del tema y la navegación desacoplada. */
            val mainViewModel: MainViewModel = viewModel()

            /** Estado observable que rastrea reactivamente si el Modo Oscuro está activo. */
            val isDarkTheme by mainViewModel.isDarkTheme.collectAsState()

            // Envoltorio del tema de la aplicación que aplica la paleta de colores según el estado `isDarkTheme`
            AppCORPALIV_grupo11Theme(darkTheme = isDarkTheme) {

                // Obtiene el contexto actual de la aplicación necesario para acceder a los servicios de red
                val context = LocalContext.current

                /**
                 * Estado mutable local que almacena si el dispositivo tiene acceso a Internet.
                 * Se inicializa mediante la función utilitaria [isInternetAvailable].
                 */
                var hasInternet by remember { mutableStateOf(isInternetAvailable(context)) }

                // =========================================================================
                // ESTRUCTURA VISUAL PRINCIPAL (SURFACE CONTAINER)
                // =========================================================================
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Evaluación condicional de conectividad a la red
                    if (hasInternet) {
                        // Renderiza el orquestador principal de pantallas y barras de navegación
                        AppNavigation(
                            isDarkTheme = isDarkTheme,
                            onToggleTheme = { mainViewModel.toggleTheme() }
                        )
                    } else {
                        // Despliega la pantalla de alerta por falta de conectividad con botón de reintento
                        NoInternetScreen(
                            onRetry = {
                                // Vuelve a consultar el gestor de conectividad al presionar el botón "Reintentar"
                                hasInternet = isInternetAvailable(context)
                            }
                        )
                    }
                }
            }
        }
    }
}