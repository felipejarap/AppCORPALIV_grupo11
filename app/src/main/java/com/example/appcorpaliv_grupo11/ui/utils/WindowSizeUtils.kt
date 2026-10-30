package com.example.appcorpaliv_grupo11.ui.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Función composable utilitaria para calcular la clase de tamaño de ventana ([WindowSizeClass]) actual.
 *
 * Permite adaptar dinámicamente el diseño de la interfaz de usuario según el tamaño de la pantalla
 * (pantallas compactas, medianas o expandidas) en arquitecturas de Compose responsivas.
 *
 * @return [WindowSizeClass] que representa los anchos y altos de la ventana activa.
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun obtenerWindowSizeClass(): WindowSizeClass {
    // Obtiene el contexto local de Jetpack Compose
    val context = LocalContext.current

    // Extrae la Activity asociada desde la jerarquía del Context
    val activity = context.findActivity()

    // Calcula y retorna la clase de tamaño de ventana usando la API de Material3
    return calculateWindowSizeClass(activity)
}

/**
 * Función de extensión recursiva por la cola (tailrec) sobre [Context] para extraer
 * la instancia de [Activity] hospedadora.
 *
 * Recorre la cadena de envoltorios [ContextWrapper] hasta encontrar el [Activity] base.
 *
 * @receiver Contexto actual de la aplicación o vista.
 * @return Instancia válida de [Activity].
 * @throws IllegalStateException Si el contexto no proviene de un entorno de Activity.
 */
private tailrec fun Context.findActivity(): Activity {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> error("No se pudo obtener la Activity desde el contexto actual.")
    }
}