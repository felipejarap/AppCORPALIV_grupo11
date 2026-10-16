package com.example.appcorpaliv_grupo11.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star // Icono para la Guía 12
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appcorpaliv_grupo11.ui.screens.HomeScreen
import com.example.appcorpaliv_grupo11.ui.screens.ProfileScreen
import com.example.appcorpaliv_grupo11.ui.screens.SettingsScreen
import com.example.appcorpaliv_grupo11.ui.screens.RegistroScreen
import com.example.appcorpaliv_grupo11.ui.screens.ResumenScreen
import com.example.appcorpaliv_grupo11.ui.screens.PantallaPrincipal // <-- Pantalla de la Guía 12
import com.example.appcorpaliv_grupo11.viewmodel.UsuarioViewModel
import com.example.appcorpaliv_grupo11.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val usuarioViewModel: UsuarioViewModel = viewModel()
    val mainViewModel: MainViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Incluimos Screen.Guia12.route en el navbar para acceder fácilmente
    val mostrarNavbar = currentRoute == null || currentRoute in listOf(
        Screen.Home.route,
        Screen.Profile.route,
        Screen.Settings.route,
        Screen.Registro.route,
        Screen.Resumen.route,
        Screen.Guia12.route // <-- Agregado aquí
    )

    // Colector de eventos del formulario (Guía 11)
    LaunchedEffect(Unit) {
        usuarioViewModel.navigationEvents.collectLatest { event ->
            when (event) {
                is NavigationEvent.NavigateTo -> {
                    navController.navigate(event.route.route) {
                        event.popupToRoute?.let { popup ->
                            popUpTo(popup.route) { inclusive = event.inclusive }
                        }
                        launchSingleTop = event.singleTop
                        restoreState = true
                    }
                }
                is NavigationEvent.PopBackStack -> navController.popBackStack()
                is NavigationEvent.NavigateUp -> navController.navigateUp()
            }
        }
    }

    // Colector de eventos generales
    LaunchedEffect(Unit) {
        mainViewModel.navigationEvents.collectLatest { event ->
            when (event) {
                is NavigationEvent.NavigateTo -> {
                    navController.navigate(event.route.route) {
                        event.popupToRoute?.let { popup ->
                            popUpTo(popup.route) { inclusive = event.inclusive }
                        }
                        launchSingleTop = event.singleTop
                        restoreState = true
                    }
                }
                is NavigationEvent.PopBackStack -> navController.popBackStack()
                is NavigationEvent.NavigateUp -> navController.navigateUp()
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (mostrarNavbar) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        selected = currentRoute == Screen.Home.route,
                        onClick = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.List, contentDescription = "Registro") },
                        label = { Text("Registro") },
                        selected = currentRoute == Screen.Registro.route,
                        onClick = {
                            navController.navigate(Screen.Registro.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                        label = { Text("Perfil") },
                        selected = currentRoute == Screen.Profile.route,
                        onClick = {
                            navController.navigate(Screen.Profile.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") },
                        label = { Text("Ajustes") },
                        selected = currentRoute == Screen.Settings.route,
                        onClick = {
                            navController.navigate(Screen.Settings.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    // Botón extra en el Navbar para probar rápidamente la Guía 12
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Star, contentDescription = "Guía 12") },
                        label = { Text("Animación") },
                        selected = currentRoute == Screen.Guia12.route,
                        onClick = {
                            navController.navigate(Screen.Guia12.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Registro.route) {
                RegistroScreen(viewModel = usuarioViewModel)
            }
            composable(route = Screen.Resumen.route) {
                ResumenScreen(viewModel = usuarioViewModel)
            }
            composable(route = Screen.Home.route) {
                HomeScreen(navController = navController, viewModel = mainViewModel)
            }
            composable(route = Screen.Profile.route) {
                ProfileScreen(navController = navController, viewModel = mainViewModel)
            }
            composable(route = Screen.Settings.route) {
                SettingsScreen(navController = navController, viewModel = mainViewModel)
            }
            // Ruta agregada para la Guía 12
            composable(route = Screen.Guia12.route) {
                PantallaPrincipal()
            }
        }
    }
}