package com.example.appcorpaliv_grupo11.ui.screens.home

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appcorpaliv_grupo11.viewModel.MainViewModel

/**
 * PANTALLA PRINCIPAL DE INICIO (HOME SCREEN - ADAPTIVE ROOT)
 *
 * Contenedor adaptativo raíz del módulo principal de la aplicación. Evalúa dinámicamente
 * la categoría de ancho de la ventana activa (WindowWidthSizeClass) y delega la maquetación
 * a la sub-vista correspondiente (HomeScreenCompacta, HomeScreenMediana y HomeScreenExpandida),
 * inyectando las referencias de navegación y la instancia del ViewModel central.
 *
 * @param navController Controlador de navegación de Jetpack Navigation para gestionar el flujo entre pantallas.
 * @param windowSizeClass Clasificación del ancho de pantalla (calculada dinámicamente si no se provee).
 * @param viewModel Instancia del ViewModel MainViewModel obtenida mediante la fábrica de Compose.
 */
@Composable
fun HomeScreen(
    navController: NavController,
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    viewModel: MainViewModel = viewModel()
) {
    // Selección declarativa de la sub-vista según el tamaño de pantalla del dispositivo
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> HomeScreenCompacta(navController, viewModel)
        WindowWidthSizeClass.Medium -> HomeScreenMediana(navController, viewModel)
        WindowWidthSizeClass.Expanded -> HomeScreenExpandida(navController, viewModel)
        else -> HomeScreenCompacta(navController, viewModel)
    }
}

/**
 * CÁLCULO DINÁMICO DE CLASIFICACIÓN DE ANCHO DE VENTANA
 *
 * Determina la categoría de ancho WindowWidthSizeClass analizando los dp disponibles
 * en la configuración local mediante LocalConfiguration.current.
 *
 * Puntos de quiebre (Breakpoints) de Material 3:
 * - < 600 dp: Compact (Smartphones portrait)
 * - 600 dp a 839 dp: Medium (Tablets portrait / Smartphones landscape)
 * - >= 840 dp: Expanded (Tablets landscape / Monitores / Desktops)
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