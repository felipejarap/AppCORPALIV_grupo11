package com.example.appcorpaliv_grupo11.ui.screens.listaContactos

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.appcorpaliv_grupo11.ui.utils.obtenerWindowSizeClass
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

@Composable
fun ListaContactosMain(viewModel: ContactoViewModel) {
    val windowSizeClass = obtenerWindowSizeClass()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> ListaContactosCompacta(viewModel = viewModel)
        WindowWidthSizeClass.Medium -> ListaContactosMediana(viewModel = viewModel)
        WindowWidthSizeClass.Expanded -> ListaContactosExpandida(viewModel = viewModel)
        else -> ListaContactosCompacta(viewModel = viewModel)
    }
}