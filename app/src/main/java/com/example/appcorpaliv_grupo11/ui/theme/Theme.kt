package com.example.appcorpaliv_grupo11.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * CONFIGURACIÓN DEL ESQUEMA DE COLORES EN MODO OSCURO (Material 3)
 */
private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    error = DarkError,
    onError = DarkOnError
)

/**
 * CONFIGURACIÓN DEL ESQUEMA DE COLORES EN MODO CLARO (Material 3)
 */
private val LightColorScheme = lightColorScheme(
    primary = PrimaryAzulCorpaliv,
    onPrimary = OnPrimaryWhite,
    secondary = SecondaryTeal,
    onSecondary = OnSecondaryWhite,
    tertiary = TertiaryAmbar,
    onTertiary = OnTertiaryWhite,
    background = SurfaceGrisClaro,
    onBackground = OnSurfaceGrisOscuro,
    surface = SurfaceGrisClaro,
    onSurface = OnSurfaceGrisOscuro,
    surfaceVariant = SurfaceVariantClaro,
    onSurfaceVariant = OnSurfaceVariantClaro,
    error = ErrorCarmesi,
    onError = OnErrorWhite
)

/**
 * COMPOSABLE CONTENEDOR DEL TEMA GLOBAL DE LA APLICACIÓN
 */
@Composable
fun AppCORPALIV_grupo11Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // Selección del esquema de colores según el tema activo
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Configuración del contraste de íconos en la barra del sistema (Status Bar)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Ajusta el contraste de los íconos (oscuros en modo claro, claros en modo oscuro)
            // eliminando el uso directo de statusBarColor para evitar la advertencia de obsolescencia.
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}