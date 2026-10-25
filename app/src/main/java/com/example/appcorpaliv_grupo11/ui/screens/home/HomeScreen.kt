package com.example.appcorpaliv_grupo11.ui.screens.home

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appcorpaliv_grupo11.viewModel.MainViewModel

/**
 * Pantalla principal de Inicio (Home).
 *
 * Decide qué diseño mostrar según el tamaño de la pantalla.
 */
@Composable
fun HomeScreen(
    navController: NavController,
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    viewModel: MainViewModel = viewModel()
) {
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> HomeScreenCompacta(navController, viewModel)
        WindowWidthSizeClass.Medium -> HomeScreenMediana(navController, viewModel)
        WindowWidthSizeClass.Expanded -> HomeScreenExpandida(navController, viewModel)
        else -> HomeScreenCompacta(navController, viewModel)
    }
}

@Composable
private fun obtenerWindowWidthSizeClass(): WindowWidthSizeClass {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 600 -> WindowWidthSizeClass.Compact
        configuration.screenWidthDp < 840 -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }
}