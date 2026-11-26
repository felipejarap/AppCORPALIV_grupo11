package com.example.appcorpaliv_grupo11.ui.screens.quienesSomos

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * PANTALLA PRINCIPAL DE QUIÉNES SOMOS / IDENTIDAD INSTITUCIONAL (ADAPTIVE ROOT CONTAINER)
 *
 * Contenedor adaptativo raíz de la sección sobre la historia, misión, visión y valores de Corpaliv.
 * Evalúa dinámicamente el ancho de pantalla mediante la clasificación WindowWidthSizeClass
 * y delega el renderizado a la sub-vista correspondiente:
 * - QuienesSomosScreenCompacta
 * - QuienesSomosScreenMediana
 * - QuienesSomosScreenExpandida
 *
 * Propaga el callback de navegación onContactoClick hacia el módulo de contacto.
 *
 * @param windowSizeClass Clasificación del ancho de pantalla (calculada dinámicamente si no se provee).
 * @param onContactoClick Callback emitido cuando el usuario solicita mayor información o contacto directo.
 */
@Composable
fun QuienesSomosScreen(
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    onContactoClick: () -> Unit = {}
) {
    // Selección declarativa de la sub-vista según la categoría de ancho del dispositivo
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> QuienesSomosScreenCompacta(onContactoClick = onContactoClick)
        WindowWidthSizeClass.Medium -> QuienesSomosScreenMediana(onContactoClick = onContactoClick)
        WindowWidthSizeClass.Expanded -> QuienesSomosScreenExpandida(onContactoClick = onContactoClick)
        else -> QuienesSomosScreenCompacta(onContactoClick = onContactoClick)
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