package com.example.appcorpaliv_grupo11.ui.screens.contacto

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

/**
 * PANTALLA PRINCIPAL DE CONTACTO (CONTENEDOR ADAPTATIVO RAÍZ)
 *
 * Evalúa dinámicamente el ancho disponible en el dispositivo mediante la categoría
 * WindowWidthSizeClass y delega la maquetación a la sub-vista correspondiente
 * (ContactoScreenCompacta, ContactoScreenMediana o ContactoScreenExpandida).
 *
 * @param windowSizeClass Clasificación del ancho de pantalla (calculada por defecto según el dispositivo).
 * @param viewModel Instancia del ViewModel ContactoViewModel obtenida mediante la fábrica de Compose.
 */
@Composable
fun ContactoScreen(
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    viewModel: ContactoViewModel = viewModel()
) {
    // Selección declarativa del diseño según el factor de forma del dispositivo
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> ContactoScreenCompacta(viewModel)
        WindowWidthSizeClass.Medium -> ContactoScreenMediana(viewModel)
        WindowWidthSizeClass.Expanded -> ContactoScreenExpandida(viewModel)
        else -> ContactoScreenCompacta(viewModel)
    }
}

/**
 * CÁLCULO DINÁMICO DE CLASIFICACIÓN DE ANCHO DE VENTANA
 *
 * Obtiene la categoría de ancho WindowWidthSizeClass analizando los dp disponibles
 * en la configuración local mediante LocalConfiguration.current.
 *
 * Puntos de quiebre (Breakpoints) estándar:
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