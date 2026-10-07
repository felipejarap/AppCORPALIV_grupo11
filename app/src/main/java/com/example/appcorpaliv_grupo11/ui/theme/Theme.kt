package com.example.appcorpaliv_grupo11.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Mantenemos este bloque por defecto por si el sistema entra en modo oscuro
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// =================================================================
// PALETA CORPORATIVA DE ALBA LAB (Mapeada con tus nuevos colores)
// =================================================================
private val LightColorScheme = lightColorScheme(
    primary = VerdeOlivaPrincipal,           // Títulos principales y barras
    onPrimary = BlancoPuro,
    primaryContainer = VerdeClaroContenedor, // Bloques destacados (como la misión)
    onPrimaryContainer = GrisTextoOscuro,
    secondary = NaranjaEnfasis,              // Subtítulos y botones de acción
    onSecondary = BlancoPuro,
    background = CremaFondo,                 // Fondo general limpio y accesible
    surface = BlancoPuro,                    // Fondo de las tarjetas individuales
    onSurface = GrisTextoOscuro
)

@Composable
fun AppCORPALIV_grupo11Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // CAMBIO CLAVE: Apagamos el color dinámico para obligar a usar la paleta de la fundación
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
