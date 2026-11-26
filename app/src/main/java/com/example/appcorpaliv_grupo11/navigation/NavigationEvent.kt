package com.example.appcorpaliv_grupo11.navigation

/**
 * CLASE SELLADA PARA EVENTOS DE NAVEGACIÓN (SEALED CLASS)
 *
 * Representa los eventos discretos ("one-shot events") de navegación por los ViewModels
 * y consumidos por la capa de interfaz de usuario.
 * Desacopla completamente la lógica de negocio del ViewModel de la instancia concreta del [NavigationEvent].
 */
sealed class NavigationEvent {

    /**
     * Solicitud para navegar hacia una nueva pantalla ([route]).
     *
     * @property route Objeto de destino definido en la clase sellada [Screen].
     * @property popupToRoute Pantalla opcional hasta la cual se limpiará la pila de navegación (backstack).
     * @property inclusive Si es `true`, la pantalla especificada en [popupToRoute] también será removida de la pila.
     * @property singleTop Si es `true`, evita crear instancias duplicadas de la pantalla de destino si ya está en la cima de la pila.
     */
    data class NavigateTo(
        val route: Screen,
        val popupToRoute: Screen? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ) : NavigationEvent()

    /** Solicitud para remover la pantalla actual y retornar a la pantalla inmediatamente anterior en la pila. */
    data object PopBackStack : NavigationEvent()

    /** Solicitud para realizar un desplazamiento hacia arriba en la jerarquía de navegación. */
    data object NavigateUp : NavigationEvent()
}