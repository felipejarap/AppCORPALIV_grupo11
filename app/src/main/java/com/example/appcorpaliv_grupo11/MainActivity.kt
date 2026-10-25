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
 * Actividad principal y punto de entrada único de la aplicación (Single Activity Architecture).
 * Se encarga de inicializar la interfaz declarativa con Jetpack Compose, aplicar la configuración
 * del tema (Modo Oscuro / Claro) y validar el estado inicial de conexión a Internet.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Define el contenido visual de la actividad utilizando Jetpack Compose
        setContent {
            // =========================================================================
            // INICIALIZACIÓN DE VIEWMODEL Y ESTADOS GLOBALES
            // =========================================================================

            /** ViewModel global para la gestión del tema y navegación. */
            val mainViewModel: MainViewModel = viewModel()

            /** Estado observable que indica si el Modo Oscuro está activo. */
            val isDarkTheme by mainViewModel.isDarkTheme.collectAsState()

            // Envoltorio del tema de la aplicación con soporte para Modo Oscuro/Claro dinámico
            AppCORPALIV_grupo11Theme(darkTheme = isDarkTheme) {

                // Contexto de Android necesario para verificar servicios del sistema (conectividad)
                val context = LocalContext.current

                /**
                 * Estado reactivo que almacena si hay conexión a Internet disponible.
                 * Se inicializa al componer la vista mediante la utilidad [isInternetAvailable].
                 */
                var hasInternet by remember { mutableStateOf(isInternetAvailable(context)) }

                // =========================================================================
                // ESTRUCTURA VISUAL PRINCIPAL (SURFACE CONTAINER)
                // =========================================================================
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Control de flujo según el estado de conectividad a la red
                    if (hasInternet) {
                        // Muestra el grafo de navegación principal de la aplicación
                        AppNavigation(
                            isDarkTheme = isDarkTheme,
                            onToggleTheme = { mainViewModel.toggleTheme() }
                        )
                    } else {
                        // Muestra la pantalla alternativa de falta de conexión con opción de reintento
                        NoInternetScreen(
                            onRetry = {
                                // Revisa nuevamente la conexión al presionar "Reintentar"
                                hasInternet = isInternetAvailable(context)
                            }
                        )
                    }
                }
            }
        }
    }
}