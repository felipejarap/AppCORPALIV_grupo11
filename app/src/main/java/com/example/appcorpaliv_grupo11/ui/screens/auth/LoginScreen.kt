package com.example.appcorpaliv_grupo11.ui.screens.auth

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.example.appcorpaliv_grupo11.viewModel.AuthViewModel

/**
 * Pantalla principal de Login.
 *
 * Selecciona el diseño adecuado (Compacto, Mediano o Expandido) según el tamaño del dispositivo.
 *
 * @param windowSizeClass Clasificación del ancho de ventana. Por defecto se calcula de forma dinámica.
 * @param authViewModel Instancia del [AuthViewModel] para la autenticación de usuarios.
 * @param onLoginSuccess Callback a ejecutar cuando el inicio de sesión sea exitoso.
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
 * Obtiene la categoría de ancho de pantalla [WindowWidthSizeClass] a partir de la [LocalConfiguration] actual.
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