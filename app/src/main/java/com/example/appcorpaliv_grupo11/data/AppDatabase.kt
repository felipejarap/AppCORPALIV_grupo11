package com.example.appcorpaliv_grupo11.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appcorpaliv_grupo11.model.ContactoEntity

/**
 * CLASE PRINCIPAL DE BASE DE DATOS LOCAL (ROOM)
 *
 * Esta clase hereda de [RoomDatabase] y actúa como el punto de acceso central a la base de datos SQLite.
 * Implementa el patrón Singleton para garantizar una única instancia activa en toda la aplicación.
 */
@Database(
    entities = [ContactoEntity::class], // Registra las tablas/entidades que maneja la BD.
    version = 2,                        // Control de versiones del esquema SQLite.
    exportSchema = false                // Desactiva la exportación de esquemas en archivos JSON.
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provee el DAO (Data Access Object) para realizar operaciones CRUD sobre la tabla de contactos.
     * Room genera automáticamente la implementación concreta de esta función en tiempo de compilación.
     */
    abstract fun contactoDao(): ContactoDao

    companion object {
        /**
         * Guardador en memoria de la instancia única de la base de datos (Patrón Singleton).
         *
         * @Volatile: Garantiza que cualquier cambio realizado en la variable [INSTANCE] sea
         * inmediatamente visible para todos los hilos de ejecución (Thread Safety), evitando
         * problemas de concurrencia.
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Retorna la instancia única de [AppDatabase] mediante el patrón "Double-Checked Locking".
         *
         * @param context Contexto global de la aplicación (`applicationContext`) para construir la BD.
         * @return Instancia singleton lista para realizar consultas.
         */
        fun getDatabase(context: Context): AppDatabase {
            // Si la instancia ya existe en memoria, la retorna directamente (Evita reconstrucciones innecesarias).
            return INSTANCE ?: synchronized(this) {
                // Si dos hilos entran al mismo tiempo, solo uno ejecuta el bloque 'synchronized' a la vez.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "corpaliv_database" // Nombre del archivo físico SQLite generado en el almacenamiento local.
                )
                    // Si se cambia la versión (ej. de 1 a 2) sin escribir una migración explícita,
                    // destruye y recrea la BD para evitar fallos de compilación/ejecución.
                    .fallbackToDestructiveMigration()
                    .build()

                // Asigna la instancia recién creada a la variable @Volatile y la devuelve.
                INSTANCE = instance
                instance
            }
        }
    }
}