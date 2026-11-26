package com.example.appcorpaliv_grupo11

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
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
 * y monitorea en tiempo real la disponibilidad de red para alternar entre la navegación principal
 * y la pantalla de contingencia offline (`NoInternetScreen`).
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val isDarkTheme by mainViewModel.isDarkTheme.collectAsState()

            AppCORPALIV_grupo11Theme(darkTheme = isDarkTheme) {
                val context = LocalContext.current

                // Estado inicial de conectividad
                var hasInternet by remember { mutableStateOf(isInternetAvailable(context)) }

                // Escucha en tiempo real si el dispositivo pierde o recupera internet mientras usa la app
                DisposableEffect(context) {
                    val connectivityManager =
                        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                    val networkCallback = object : ConnectivityManager.NetworkCallback() {
                        override fun onAvailable(network: Network) {
                            hasInternet = true
                        }

                        override fun onLost(network: Network) {
                            hasInternet = isInternetAvailable(context)
                        }

                        override fun onCapabilitiesChanged(
                            network: Network,
                            networkCapabilities: NetworkCapabilities
                        ) {
                            hasInternet = networkCapabilities.hasCapability(
                                NetworkCapabilities.NET_CAPABILITY_INTERNET
                            )
                        }
                    }

                    val request = NetworkRequest.Builder()
                        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        .build()

                    try {
                        connectivityManager.registerNetworkCallback(request, networkCallback)
                    } catch (e: Exception) {
                        hasInternet = isInternetAvailable(context)
                    }

                    onDispose {
                        try {
                            connectivityManager.unregisterNetworkCallback(networkCallback)
                        } catch (_: Exception) {
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (hasInternet) {
                        AppNavigation(
                            isDarkTheme = isDarkTheme,
                            onToggleTheme = { mainViewModel.toggleTheme() }
                        )
                    } else {
                        NoInternetScreen(
                            onRetry = {
                                hasInternet = isInternetAvailable(context)
                            }
                        )
                    }
                }
            }
        }
    }
}