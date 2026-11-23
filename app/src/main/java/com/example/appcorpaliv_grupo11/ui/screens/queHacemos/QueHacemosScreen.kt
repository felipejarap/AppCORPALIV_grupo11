package com.example.appcorpaliv_grupo11.ui.screens.queHacemos

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * PANTALLA PRINCIPAL DE QUÉ HACEMOS / SERVICIOS E IMPACTO SOCIAL (ADAPTIVE ROOT CONTAINER)
 *
 * Contenedor adaptativo raíz de la sección informativa de programas, servicios y líneas de acción de Corpaliv.
 * Evalúa dinámicamente el ancho de pantalla mediante la clasificación WindowWidthSizeClass
 * y delega el renderizado a la sub-vista correspondiente:
 * - QueHacemosScreenCompacta
 * - QueHacemosScreenMediana
 * - QueHacemosScreenExpandida
 *
 * Propaga el callback de navegación onContactoClick hacia el formulario de contacto o catálogo.
 *
 * @param windowSizeClass Clasificación del ancho de pantalla (calculada dinámicamente si no se provee).
 * @param onContactoClick Callback emitido cuando el usuario solicita mayor información o contacto directo.
 */
@Composable
fun QueHacemosScreen(
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    onContactoClick: () -> Unit = {}
) {
    // Enrutamiento declarativo según el ancho de ventana del dispositivo
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> QueHacemosScreenCompacta(onContactoClick = onContactoClick)
        WindowWidthSizeClass.Medium -> QueHacemosScreenMediana(onContactoClick = onContactoClick)
        WindowWidthSizeClass.Expanded -> QueHacemosScreenExpandida(onContactoClick = onContactoClick)
        else -> QueHacemosScreenCompacta(onContactoClick = onContactoClick)
    }
}

/**
 * CÁLCULO DINÁMICO DE CLASIFICACIÓN DE ANCHO DE VENTANA
 *
 * Determina la categoría de ancho WindowWidthSizeClass analizando los dp disponibles
 * en la configuración local mediante LocalConfiguration.current.
 *
 * Puntos de quiebre (Breakpoints) estándar de Material Design 3:
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