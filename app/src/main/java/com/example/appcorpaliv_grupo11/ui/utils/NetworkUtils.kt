package com.example.appcorpaliv_grupo11.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Función verifica la disponibilidad real de conexión a Internet en el dispositivo.
 *
 * Evalúa las capacidades de la red activa utilizando el [ConnectivityManager] de Android,
 * garantizando no solo que la red esté conectada, sino que tenga acceso a Internet validado.
 *
 * @param context Contexto de la aplicación necesario para acceder al servicio del sistema.
 * @return `true` si el dispositivo cuenta con una red activa con acceso a Internet validado, `false` en caso contrario.
 */
fun isInternetAvailable(context: Context): Boolean {
    // Obtiene el servicio del sistema encargado de la gestión de red
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Obtiene la red activa actual; si es nula, no hay ninguna red conectada
    val network = connectivityManager.activeNetwork ?: return false

    // Obtiene las capacidades de la red activa; si es nulo, no se pueden determinar sus propiedades
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    // Valida que la red tenga capacidad de conectarse a Internet (NET_CAPABILITY_INTERNET)
    // y que Android haya verificado el acceso efectivo a la red externa (NET_CAPABILITY_VALIDATED)
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}