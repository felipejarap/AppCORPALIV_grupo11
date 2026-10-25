package com.example.appcorpaliv_grupo11.ui.screens.contacto

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

/**
 * Pantalla principal de Contacto.
 * Decide qué diseño mostrar según el tamaño de la pantalla.
 */
@Composable
fun ContactoScreen(
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    viewModel: ContactoViewModel = viewModel()
) {
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> ContactoScreenCompacta(viewModel)
        WindowWidthSizeClass.Medium -> ContactoScreenMediana(viewModel)
        WindowWidthSizeClass.Expanded -> ContactoScreenExpandida(viewModel)
        else -> ContactoScreenCompacta(viewModel)
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