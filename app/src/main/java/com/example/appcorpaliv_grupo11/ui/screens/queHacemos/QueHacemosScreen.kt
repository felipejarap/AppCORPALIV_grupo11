package com.example.appcorpaliv_grupo11.ui.screens.queHacemos

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.appcorpaliv_grupo11.ui.screens.leyInclusion.obtenerWindowWidthSizeClass
import androidx.compose.ui.platform.LocalConfiguration
import com.example.appcorpaliv_grupo11.ui.screens.quienesSomos.QuienesSomosScreenCompacta
import com.example.appcorpaliv_grupo11.ui.screens.quienesSomos.QuienesSomosScreenExpandida
import com.example.appcorpaliv_grupo11.ui.screens.quienesSomos.QuienesSomosScreenMediana

@Composable
fun QueHacemosScreen(
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    onContactoClick: () -> Unit = {}
) {
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> QueHacemosScreenCompacta(onContactoClick = onContactoClick)
        WindowWidthSizeClass.Medium -> QueHacemosScreenMediana(onContactoClick = onContactoClick)
        WindowWidthSizeClass.Expanded -> QueHacemosScreenExpandida(onContactoClick = onContactoClick)
        else -> QueHacemosScreenCompacta(onContactoClick = onContactoClick)
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
