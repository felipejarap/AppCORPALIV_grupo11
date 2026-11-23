package com.example.appcorpaliv_grupo11.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * FUNCIÓN UTILITARIA DE COMPROBACIÓN DE CONECTIVIDAD A INTERNET
 *
 * Verifica la disponibilidad real de conexión a Internet en el dispositivo.
 * Evalúa las capacidades de la red activa utilizando el [ConnectivityManager] de Android,
 * garantizando no solo que el dispositivo esté asociado a una red (wifi, datos móviles o Ethernet),
 * sino que dicha red tenga transporte de datos validado hacia el exterior.
 *
 * @param context Contexto de la aplicación necesario para acceder a los servicios del sistema.
 * @return `true` si el dispositivo cuenta con una red activa con acceso a Internet verificado, `false` en caso contrario.
 */
fun isInternetAvailable(context: Context): Boolean {
    // Obtiene el servicio del sistema encargado de la gestión de conectividad de red
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Obtiene la instancia de la red activa actual; si es null, no hay ninguna interfaz conectada
    val network = connectivityManager.activeNetwork ?: return false

    // Consulta los descriptores de capacidad de la red activa; si es null, no se pueden verificar sus facultades
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    // Evalúa conjuntamente dos banderas fundamentales del sistema operativo:
    // 1. NET_CAPABILITY_INTERNET: La red tiene la capacidad teórica de enrutar tráfico hacia la red pública.
    // 2. NET_CAPABILITY_VALIDATED: Android ha probado exitosamente un socket de prueba confirmando acceso real a Internet.
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}