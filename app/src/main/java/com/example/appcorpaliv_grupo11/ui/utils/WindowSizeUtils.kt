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
 * FUNCIÓN COMPOSABLE UTILITARIA DE TAMAÑO DE VENTANA
 *
 * Calcula y retorna la clase de tamaño de ventana ([WindowSizeClass]) activa.
 * Permite adaptar dinámicamente el diseño de la interfaz de usuario en tiempo de ejecución
 * (pantallas compactas, medianas o expandidas) en arquitecturas responsivas de Jetpack Compose.
 *
 * @return [WindowSizeClass] que clasifica los anchos y altos de la ventana activa.
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun obtenerWindowSizeClass(): WindowSizeClass {
    // Obtiene el contexto local activo de Jetpack Compose
    val context = LocalContext.current

    // Extrae la Activity hospeda desde la jerarquía de contextos usando la extensión `findActivity()`
    val activity = context.findActivity()

    // Calcula y retorna la clase de tamaño de ventana oficial de la librería Material3
    return calculateWindowSizeClass(activity)
}

/**
 * FUNCIÓN DE EXTENSIÓN RECURSIVA PARA OBTENER LA ACTIVITY
 *
 * Función de extensión optimizada mediante recursión por la cola (`tailrec`) sobre [Context].
 * Recorre en profundidad la cadena de envoltorios de contextos ([ContextWrapper]) hasta encontrar
 * la instancia base de la [Activity] que hospeda la vista.
 *
 * @receiver Contexto actual expuesto por la vista o aplicación.
 * @return Instancia concreta de la [Activity] hospedadora.
 * @throws IllegalStateException Si la cadena de contextos no proviene de una Activity válida.
 */
private tailrec fun Context.findActivity(): Activity {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> error("No se pudo obtener la Activity desde el contexto actual.")
    }
}