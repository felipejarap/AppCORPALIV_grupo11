package com.example.appcorpaliv_grupo11.ui.screens.leyInclusion

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * PANTALLA PRINCIPAL DE LEY DE INCLUSIÓN LABORAL (ADAPTIVE ROOT CONTAINER)
 *
 * Contenedor adaptativo raíz del módulo informativo de la Ley 21.015 / MIDESO.
 * Evalúa dinámicamente el ancho disponible en la pantalla mediante la clasificación
 * WindowWidthSizeClass y delega el renderizado a la sub-vista correspondiente
 * (LeyInclusionScreenCompacta, LeyInclusionScreenMediana o LeyInclusionScreenExpandida),
 * propagando el callback de navegación hacia el formulario de contacto.
 *
 * @param windowSizeClass Clasificación del ancho de pantalla (calculada dinámicamente si no se provee).
 * @param onContactarClick Callback emitido cuando el usuario solicita asesoría o contacto directo.
 */
@Composable
fun LeyInclusionScreen(
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    onContactarClick: () -> Unit = {}
) {
    // Selección declarativa de la sub-vista según la categoría de ancho del dispositivo
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> LeyInclusionScreenCompacta(onContactarClick = onContactarClick)
        WindowWidthSizeClass.Medium -> LeyInclusionScreenMediana(onContactarClick = onContactarClick)
        WindowWidthSizeClass.Expanded -> LeyInclusionScreenExpandida(onContactarClick = onContactarClick)
        else -> LeyInclusionScreenCompacta(onContactarClick = onContactarClick)
    }
}

/**
 * CÁLCULO DINÁMICO DE CLASIFICACIÓN DE ANCHO DE VENTANA
 *
 * Obtiene la categoría de ancho WindowWidthSizeClass analizando los dp disponibles
 * en la configuración local mediante LocalConfiguration.current.
 *
 * Puntos de quiebre (Breakpoints) estándar de Material Design 3:
 * - < 600 dp: Compact (Smartphones portrait)
 * - 600 dp a 839 dp: Medium (Tablets portrait / Smartphones landscape)
 * - >= 840 dp: Expanded (Tablets landscape / Monitores / Desktops)
 */
@Composable
fun obtenerWindowWidthSizeClass(): WindowWidthSizeClass {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 600 -> WindowWidthSizeClass.Compact
        configuration.screenWidthDp < 840 -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }
}