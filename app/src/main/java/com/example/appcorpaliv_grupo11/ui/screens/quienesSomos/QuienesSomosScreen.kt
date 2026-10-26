package com.example.appcorpaliv_grupo11.ui.screens.quienesSomos

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.appcorpaliv_grupo11.ui.screens.leyInclusion.obtenerWindowWidthSizeClass
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun QuienesSomosScreen(
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    onContactoClick: () -> Unit = {}
) {
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> QuienesSomosScreenCompacta(onContactoClick = onContactoClick)
        WindowWidthSizeClass.Medium -> QuienesSomosScreenMediana(onContactoClick = onContactoClick)
        WindowWidthSizeClass.Expanded -> QuienesSomosScreenExpandida(onContactoClick = onContactoClick)
        else -> QuienesSomosScreenCompacta(onContactoClick = onContactoClick)
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
