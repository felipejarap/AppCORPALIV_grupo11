package com.example.appcorpaliv_grupo11.navigation

/**
 * Clase sellada (Sealed Class) que define todas las rutas de navegación tipadas y seguras
 * dentro de la aplicación para su uso con Jetpack Compose Navigation.
 *
 * Cada destino está representado por un objeto único con su identificador de ruta único en String.
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

    data object QuienesSomos : Screen("quienes_somos")

    data object QueHacemos : Screen("que_hacemos")

    /** Pantalla de autenticación/login para funcionarios y administradores. */
    data object Login : Screen(route = "login")


}