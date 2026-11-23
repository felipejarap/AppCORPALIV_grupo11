package com.example.appcorpaliv_grupo11.ui.screens.auth

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.example.appcorpaliv_grupo11.viewModel.AuthViewModel

/**
 * PANTALLA PRINCIPAL DE LOGIN (CONTENEDOR ADAPTATIVO)
 *
 * Evalúa el ancho disponible en la pantalla del dispositivo mediante WindowWidthSizeClass
 * y delega la maquetación a la vista correspondiente (Compacta, Mediana o Expandida).
 *
 * @param windowSizeClass Clasificación del ancho de pantalla (calculada dinámicamente si no se provee).
 * @param authViewModel Instancia del ViewModel para la gestión de credenciales y estado de sesión.
 * @param onLoginSuccess Evento a ejecutar cuando la autenticación resulta exitosa.
 */
@Composable
fun LoginScreen(
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit = {}
) {
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> LoginScreenCompacta(authViewModel, onLoginSuccess)
        WindowWidthSizeClass.Medium -> LoginScreenMediana(authViewModel, onLoginSuccess)
        WindowWidthSizeClass.Expanded -> LoginScreenExpandida(authViewModel, onLoginSuccess)
        else -> LoginScreenCompacta(authViewModel, onLoginSuccess)
    }
}

/**
 * FUNCIÓN AUXILIAR DE CÁLCULO DE TAMAÑO DE VENTANA
 *
 * Obtiene la categoría de ancho WindowWidthSizeClass analizando los dp disponibles
 * en la configuración local mediante LocalConfiguration.current.
 */
@Composable
private fun obtenerWindowWidthSizeClass(): WindowWidthSizeClass {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 600 -> WindowWidthSizeClass.Compact
        configuration.screenWidthDp < 840 -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }
}