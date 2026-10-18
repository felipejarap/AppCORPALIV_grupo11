package com.example.appcorpaliv_grupo11.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appcorpaliv_grupo11.R
import com.example.appcorpaliv_grupo11.ui.screens.HomeScreen
import com.example.appcorpaliv_grupo11.ui.screens.PantallaPrincipal
import com.example.appcorpaliv_grupo11.ui.screens.ProfileScreen
import com.example.appcorpaliv_grupo11.ui.screens.RegistroScreen
import com.example.appcorpaliv_grupo11.ui.screens.ResumenScreen
import com.example.appcorpaliv_grupo11.ui.screens.SettingsScreen
import com.example.appcorpaliv_grupo11.ui.screens.contacto.ContactoScreenMain
import com.example.appcorpaliv_grupo11.viewModel.MainViewModel
import com.example.appcorpaliv_grupo11.viewModel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Detecta si es pantalla compacta (< 600dp)
    val isCompact = esPantallaCompacta()

    // El menú inicia cerrado en modo tablet / horizontal
    var isMenuExpanded by remember { mutableStateOf(false) }

    val usuarioViewModel: UsuarioViewModel = viewModel()
    val mainViewModel: MainViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val mostrarNavbar = currentRoute == null || currentRoute in listOf(
        Screen.Home.route,
        Screen.Profile.route,
        Screen.Settings.route,
        Screen.Registro.route,
        Screen.Resumen.route,
        Screen.Guia12.route,
        Screen.Contacto.route
    )

    // Colectores de eventos de navegación
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

    val menuItems = listOf(
        Triple(Screen.Home.route, "Home", Icons.Default.Home),
        Triple(Screen.Registro.route, "Registro", Icons.AutoMirrored.Filled.List),
        Triple(Screen.Profile.route, "Perfil", Icons.Default.Person),
        Triple(Screen.Settings.route, "Ajustes", Icons.Default.Settings),
        Triple(Screen.Guia12.route, "Animación", Icons.Default.Star),
        Triple(Screen.Contacto.route, "Contacto", Icons.Default.Email)
    )

    Scaffold(
        // TopAppBar para celulares en vertical (Compacta)
        topBar = {
            if (mostrarNavbar && isCompact) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_alba_lab),
                                contentDescription = "Logo Alba Lab",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .height(36.dp)
                                    .wrapContentWidth()
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Alba Lab",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        },
        // NavigationBar inferior para celulares en vertical (Compacta)
        bottomBar = {
            if (mostrarNavbar && isCompact) {
                NavigationBar {
                    menuItems.forEach { (route, label, icon) ->
                        NavigationBarItem(
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) },
                            selected = currentRoute == route,
                            onClick = {
                                navController.navigate(route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Menú lateral colapsable para pantallas Medianas y Extendidas (Tablets / Horizontal)
            if (mostrarNavbar && !isCompact) {

                // NUEVA FUNCIONALIDAD: Animación de ancho basada en Spring (efecto muelle suave)
                val menuWidth by animateDpAsState(
                    targetValue = if (isMenuExpanded) 240.dp else 64.dp,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    label = "MenuWidthAnimation"
                )

                Surface(
                    modifier = Modifier
                        .width(menuWidth) // Aplicamos el ancho animado
                        .fillMaxHeight(),
                    color = MaterialTheme.colorScheme.background,
                    tonalElevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 16.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = if (isMenuExpanded) Arrangement.SpaceBetween else Arrangement.Center
                        ) {
                            // NUEVA FUNCIONALIDAD: AnimatedVisibility para que el logo y el texto aparezcan con fundido
                            // y eviten deformaciones o saltos bruscos mientras el menú se expande.
                            AnimatedVisibility(
                                visible = isMenuExpanded,
                                enter = fadeIn(animationSpec = tween(200)),
                                exit = fadeOut(animationSpec = tween(100))
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(id = R.drawable.logo_alba_lab),
                                        contentDescription = "Logo Alba Lab",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .height(36.dp)
                                            .wrapContentWidth()
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Alba Lab",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        maxLines = 1
                                    )
                                }
                            }

                            IconButton(onClick = { isMenuExpanded = !isMenuExpanded }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Abrir/Cerrar Menú",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        // NUEVA FUNCIONALIDAD: AnimatedVisibility para las opciones del menú lateral.
                        // Se ocultan instantáneamente al cerrar y aparecen con fundido al abrirse.
                        AnimatedVisibility(
                            visible = isMenuExpanded,
                            enter = fadeIn(animationSpec = tween(250)),
                            exit = fadeOut(animationSpec = tween(100))
                        ) {
                            Column {
                                HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))

                                menuItems.forEach { (route, label, icon) ->
                                    NavigationDrawerItem(
                                        icon = { Icon(icon, contentDescription = label) },
                                        label = { Text(label) },
                                        selected = currentRoute == route,
                                        onClick = {
                                            navController.navigate(route) {
                                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        modifier = Modifier.padding(horizontal = 4.dp),
                                        shape = MaterialTheme.shapes.medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Contenedor principal de las pantallas con animación moderna de Zoom y Fade
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                enterTransition = {
                    fadeIn(animationSpec = tween(350)) + scaleIn(
                        initialScale = 0.95f,
                        animationSpec = tween(350)
                    )
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(350)) + scaleOut(
                        targetScale = 1.05f,
                        animationSpec = tween(350)
                    )
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(350))
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(350)) + scaleOut(
                        targetScale = 0.95f,
                        animationSpec = tween(350)
                    )
                }
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
                composable(route = Screen.Guia12.route) {
                    PantallaPrincipal()
                }
                composable(route = Screen.Contacto.route) {
                    ContactoScreenMain()
                }
            }
        }
    }
}

/**
 * Función helper para comprobar si la pantalla es compacta (< 600dp).
 */
@Composable
private fun esPantallaCompacta(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp < 600
}