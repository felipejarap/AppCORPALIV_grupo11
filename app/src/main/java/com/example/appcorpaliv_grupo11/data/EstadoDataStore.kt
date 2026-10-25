package com.example.appcorpaliv_grupo11.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Extensión de la propiedad [Context] para inicializar e instanciar una única fuente
 * de preferencias persistentes basada en Jetpack DataStore ("user_preferences").
 */
private val Context.dataStore by preferencesDataStore(name = "user_preferences")

/**
 * Clase encargada del acceso y gestión de preferencias locales en el disco utilizando DataStore.
 * Almacena pares clave-valor ligeros de manera asíncrona y segura ante fallos de hilos (Thread-Safe).
 *
 * @param context Contexto de la aplicación requerido para acceder a la instancia de DataStore.
 */
class EstadoDataStore(private val context: Context) {

    companion object {
        /**
         * Clave de tipo Booleano para consultar y actualizar la preferencia del Modo Oscuro.
         */
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    }

    /**
     * Flujo observable que emite en tiempo real la preferencia del Modo Oscuro guardada en el dispositivo.
     * Lee los datos de forma asíncrona mediante un [Flow]; si la clave no existe aún en el archivo,
     * retorna `false` por defecto (Modo Claro predeterminado).
     */
    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE] ?: false
    }

    /**
     * Escribe y guarda el nuevo estado de la preferencia en el archivo de DataStore de forma asíncrona.
     *
     * @param isDark `true` para activar el Modo Oscuro, `false` para el Modo Claro.
     */
    suspend fun saveDarkModeState(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDark
        }
    }
}