package com.example.appcorpaliv_grupo11.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appcorpaliv_grupo11.model.ContactoEntity

/**
 * Base de datos principal de la aplicación construida con Room (Persistencia SQLite).
 * Configura las entidades registradas, la versión del esquema y expone los DAOs correspondientes.
 */
@Database(
    entities = [ContactoEntity::class], // Lista de clases de entidad mapeadas a tablas en SQLite
    version = 2,                        // Versión del esquema de la BD (incrementada al modificar la estructura)
    exportSchema = false                // Desactiva la exportación del esquema JSON en disco para compilación limpia
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Define el método abstracto para acceder al DAO de gestión de contactos.
     * Room genera automáticamente la implementación concreta de esta función.
     */
    abstract fun contactoDao(): ContactoDao

    companion object {
        /**
         * Referencia guardada en caché a la instancia única de la base de datos (Singleton Pattern).
         * La anotación [@Volatile] asegura que las modificaciones realizadas por un hilo
         * sean inmediatamente visibles para todos los demás hilos de ejecución.
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Devuelve la instancia única de [AppDatabase], creándola si no existe previamente.
         * Aplica el patrón "Double-Checked Locking" para garantizar un acceso seguro entre múltiples hilos (Thread-Safe).
         *
         * @param context Contexto de la aplicación utilizado para inicializar la base de datos de Room.
         */
        fun getDatabase(context: Context): AppDatabase {
            // Retorna la instancia si ya está creada; de lo contrario, entra al bloque sincronizado
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "corpaliv_database" // Nombre físico del archivo de la base de datos SQLite
                )
                    // En fase de desarrollo, si cambia la versión del esquema sin una migración explícita,
                    // destruye y recrea las tablas para evitar fallos catastróficos de lectura.
                    .fallbackToDestructiveMigration()
                    .build()

                // Asigna la instancia recién construida a la variable Singleton y la retorna
                INSTANCE = instance
                instance
            }
        }
    }
}