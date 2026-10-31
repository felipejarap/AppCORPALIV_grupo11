package com.example.appcorpaliv_grupo11.navigation

/**
 * CLASE SELLADA DE RUTAS DE NAVEGACIÓN (SEALED CLASS)
 *
 * Centraliza y define de manera segura (Type-Safe) todas las rutas de navegación de la aplicación.
 * Previene errores de escritura en cadenas de texto (typos) al navegar entre pantallas con Jetpack Compose Navigation.
 *
 * @property route Cadena de texto única que identifica la pantalla en el NavHost.
 */
sealed class Screen(val route: String) {

    /** Pantalla principal o de bienvenida de la aplicación. */
    data object Home : Screen(route = "home_page")

    /** Pantalla del formulario de contacto público. */
    data object Contacto : Screen(route = "contacto")

    /** Pantalla de administración y gestión de la lista de mensajes (requiere autenticación). */
    data object ListaContactos : Screen(route = "lista_contactos")

    /** Pantalla informativa sobre la Ley de Inclusión Laboral y Social. */
    data object LeyInclusion : Screen(route = "ley_inclusion")

    /** Pantalla con información institucional sobre la organización ("Quiénes Somos"). */
    data object QuienesSomos : Screen("quienes_somos")

    /** Pantalla descriptiva sobre las actividades y proyectos de la organización ("Qué Hacemos"). */
    data object QueHacemos : Screen("que_hacemos")

    /** Pantalla de autenticación/login para funcionarios y administradores. */
    data object Login : Screen(route = "login")
}