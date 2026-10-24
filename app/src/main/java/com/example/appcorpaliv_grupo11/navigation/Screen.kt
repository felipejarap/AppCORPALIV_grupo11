package com.example.appcorpaliv_grupo11.navigation

sealed class Screen(val route: String) {
    data object Home : Screen(route = "home_page")
    data object Profile : Screen(route = "profile_page")
    data object Settings : Screen(route = "settings_page")

    data object Registro : Screen("registro")

    data object Resumen : Screen("resumen")

    // Nueva ruta agregada para la Guía 12 (Animaciones y Estados)
    data object Guia12 : Screen("guia_12")

    data object Contacto : Screen("contacto")

    object ListaContactos : Screen("lista_contactos")
}
