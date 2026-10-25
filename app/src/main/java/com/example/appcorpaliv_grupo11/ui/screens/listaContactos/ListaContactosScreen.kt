package com.example.appcorpaliv_grupo11.ui.screens.listaContactos

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

/**
 * Pantalla principal de Gestión de Mensajes.
 * Decide qué diseño mostrar según el tamaño de la pantalla.
 */
@Composable
fun ListaContactosScreen(
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    viewModel: ContactoViewModel = viewModel()
) {
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> ListaContactosCompacta(viewModel)
        WindowWidthSizeClass.Medium -> ListaContactosMediana(viewModel)
        WindowWidthSizeClass.Expanded -> ListaContactosMediana(viewModel)
        else -> ListaContactosCompacta(viewModel)
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