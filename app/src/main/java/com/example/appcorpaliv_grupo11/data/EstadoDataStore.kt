package com.example.appcorpaliv_grupo11.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * EXTENSIÓN DE CONTEXTO PARA DATASTORE
 *
 * Crea una única instancia de DataStore de preferencias llamada "user_preferences".
 * La propiedad Delegada `by preferencesDataStore` garantiza que solo exista un archivo
 * de preferencias abierto en la aplicación (Patrón Singleton implícito).
 */
private val Context.dataStore by preferencesDataStore(name = "user_preferences")

/**
 * CLASE DE GESTIÓN DE PREFERENCIAS (DataStore Preferences)
 *
 * Esta clase sustituye al antiguo `SharedPreferences`. Permite guardar y leer pares
 * clave-valor ligeros de forma totalmente asíncrona mediante Corrutinas y Flow.
 *
 * @param context Contexto requerido para acceder al almacenamiento local del sistema.
 */
class EstadoDataStore(private val context: Context) {

    companion object {
        /**
         * Clave de preferencia de tipo Booleano (`booleanPreferencesKey`).
         * Define el identificador único con el que se guardará el estado del tema en memoria.
         */
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    }

    /**
    -      * Flujo observable (`Flow<Boolean>`) para leer la preferencia del Modo Oscuro.
     *
     * - `context.dataStore.data`: Accede al mapa de preferencias en segundo plano.
     * - `.map { preferences -> ... }`: Transforma la emisión del mapa extrayendo la clave deseada.
     * - `preferences[IS_DARK_MODE] ?: false`: Si la clave aún no existe en el disco
     *   (ej. primera ejecución de la app), asigna `false` (Modo Claro) como valor por defecto.
     */
    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE] ?: false
    }

    /**
     * Guarda de forma asíncrona el nuevo estado del tema visual en el disco.
     *
     * @param isDark `true` para activar el Modo Oscuro, `false` para desactivarlo.
     *
     * - `suspend`: Indica que se debe ejecutar de forma asíncrona sin bloquear el hilo principal.
     * - `context.dataStore.edit`: Función de transacción atómica que modifica y persiste el valor en el archivo local.
     */
    suspend fun saveDarkModeState(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDark
        }
    }
}