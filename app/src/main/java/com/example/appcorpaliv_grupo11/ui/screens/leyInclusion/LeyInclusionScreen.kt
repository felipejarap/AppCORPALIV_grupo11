package com.example.appcorpaliv_grupo11.ui.screens.leyInclusion

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * Pantalla principal de Ley de Inclusión Laboral (Ley 21.015 / MIDESO).
 * Decide qué diseño mostrar según el tamaño de la pantalla.
 */
@Composable
fun LeyInclusionScreen(
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    onContactarClick: () -> Unit = {}
) {
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> LeyInclusionScreenCompacta(onContactarClick = onContactarClick)
        WindowWidthSizeClass.Medium -> LeyInclusionScreenMediana(onContactarClick = onContactarClick)
        WindowWidthSizeClass.Expanded -> LeyInclusionScreenExpandida(onContactarClick = onContactarClick)
        else -> LeyInclusionScreenCompacta(onContactarClick = onContactarClick)
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