package com.example.appcorpaliv_grupo11.ui.screens.quienesSomos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcorpaliv_grupo11.ui.screens.leyInclusion.obtenerWindowWidthSizeClass
import androidx.compose.foundation.lazy.items
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
