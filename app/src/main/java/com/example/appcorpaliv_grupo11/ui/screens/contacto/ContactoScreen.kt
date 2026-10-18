package com.example.appcorpaliv_grupo11.ui.screens.contacto

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

/**
 * Pantalla principal de Contacto.
 * Decide qué diseño mostrar según el tamaño de la pantalla.
 */
@Composable
fun ContactoScreen(
    windowSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    viewModel: ContactoViewModel = viewModel()
)
{
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> ContactoScreenCompacta(viewModel)
        WindowWidthSizeClass.Medium -> ContactoScreenMediana(viewModel)
        WindowWidthSizeClass.Expanded -> ContactoScreenExpandida(viewModel)
        else -> ContactoScreenCompacta(viewModel)
    }
}