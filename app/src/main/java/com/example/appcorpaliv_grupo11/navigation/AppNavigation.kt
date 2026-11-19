package com.example.appcorpaliv_grupo11.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appcorpaliv_grupo11.R
import com.example.appcorpaliv_grupo11.data.AppDatabase
import com.example.appcorpaliv_grupo11.repository.ContactoRepository
import com.example.appcorpaliv_grupo11.ui.screens.home.HomeScreen
import com.example.appcorpaliv_grupo11.ui.screens.auth.LoginScreen
import com.example.appcorpaliv_grupo11.ui.screens.contacto.ContactoScreen
import com.example.appcorpaliv_grupo11.ui.screens.leyInclusion.LeyInclusionScreen
import com.example.appcorpaliv_grupo11.ui.screens.listaContactos.ListaContactosScreen
import com.example.appcorpaliv_grupo11.ui.screens.queHacemos.QueHacemosScreen
import com.example.appcorpaliv_grupo11.ui.screens.quienesSomos.QuienesSomosScreen
import com.example.appcorpaliv_grupo11.viewModel.AuthViewModel
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel
import com.example.appcorpaliv_grupo11.viewModel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * COMPONENTE PRINCIPAL DE NAVEGACIÓN Y ESTRUCTURA DE LA APP (APP NAVIGATION)
 *
 * Contenedor orquestador de la interfaz. Define el marco visual (Scaffold, Drawer, BottomBar, TopBar)
 * y mapea las rutas del NavHost con animaciones de transición. Se adapta dinámicamente según el tamaño
 * de pantalla (Compacta vs Mediana/Expandida).
 *
 * @param isDarkTheme Estado booleano actual del tema (Modo Oscuro).
 * @param onToggleTheme Callback emitido al presionar el Switch para alternar el tema visual.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    isDarkTheme: Boolean = false,
    onToggleTheme: () -> Unit = {}
) {
    // Controlador principal de la pila de navegación de Compose
    val navController = rememberNavController()
    // Determina si el dispositivo actual es de pantalla compacta (< 600 dp)
    val isCompact = esPantallaCompacta()

    // Estado del menú lateral desplegable (Drawer) y scope para corrutinas
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    // Controla si el panel lateral expandible en tablets/escritorio está abierto o contraído
    var isMenuExpanded by remember { mutableStateOf(false) }

    // Instanciación de ViewModels requeridos para la navegación global y sesión
    val mainViewModel: MainViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()

    // Observa el estado de sesión autenticada en tiempo real
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    // Rastrea la ruta activa actualmente en el NavController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Valida si la ruta actual debe mostrar la barra de navegación (Navbar)
    val mostrarNavbar = currentRoute == null || currentRoute in listOf(
        Screen.Home.route,
        Screen.LeyInclusion.route,
        Screen.QuienesSomos.route,
        Screen.QueHacemos.route,
        Screen.Contacto.route,
        Screen.ListaContactos.route,
        Screen.Login.route
    )

    // Función lambda auxiliar para ejecutar navegación limpia evitando duplicación de destinos en el historial
    val navegarA: (String) -> Unit = { route ->
        if (currentRoute != route) {
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = route != Screen.Home.route
                }
                launchSingleTop = true
                restoreState = route != Screen.Home.route
            }
        }
    }

    // Escucha eventos de navegación unidireccionales emitidos desatendidamente por el MainViewModel
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

    // Colección de ítems para la barra inferior (BottomBar) exclusiva de smartphones
    val bottomNavItems = listOf(
        Triple(Screen.Home.route, "Home", Icons.Default.Home),
        Triple(Screen.LeyInclusion.route, "Ley Inclusión", Icons.Default.Gavel),
        Triple(Screen.Contacto.route, "Contacto", Icons.Default.Email)
    )

    // Colección de secciones informativas para el menú hamburguesa (Drawer) móvil
    val infoMenuItemsCompact = listOf(
        Triple(Screen.QuienesSomos.route, "Quiénes Somos", Icons.Default.Info),
        Triple(Screen.QueHacemos.route, "Que hacemos", Icons.Default.Info)
    )

    // Colección completa de menús para el riel lateral en pantallas anchas (Tablets/Desktops)
    val publicMenuItemsExpanded = listOf(
        Triple(Screen.Home.route, "Home", Icons.Default.Home),
        Triple(Screen.LeyInclusion.route, "Ley Inclusión", Icons.Default.Gavel),
        Triple(Screen.QuienesSomos.route, "Quiénes Somos", Icons.Default.Info),
        Triple(Screen.QueHacemos.route, "Que hacemos", Icons.Default.Info),
        Triple(Screen.Contacto.route, "Contacto", Icons.Default.Email)
    )

    // Menú Desplegable Modal (ModalNavigationDrawer) para dispositivos móviles
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isCompact && mostrarNavbar,
        drawerContent = {
            if (isCompact && mostrarNavbar) {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Cabecera del Drawer con Isotipo y Marca de la aplicación
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surface,
                                tonalElevation = 1.dp
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo_alba_lab),
                                    contentDescription = "Logo Alba Lab",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .height(38.dp)
                                        .padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Alba Lab",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(bottom = 12.dp))

                        // Ítems de páginas informativas
                        infoMenuItemsCompact.forEach { (route, label, icon) ->
                            NavigationDrawerItem(
                                icon = { Icon(icon, contentDescription = label) },
                                label = { Text(label) },
                                selected = currentRoute == route,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    navegarA(route)
                                },
                                modifier = Modifier.padding(vertical = 2.dp),
                                shape = MaterialTheme.shapes.medium
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                        // Fila con el conmutador de tema (Switch Modo Oscuro)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                                    contentDescription = "Tema Oscuro",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Modo Oscuro",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Switch(
                                checked = isDarkTheme,
                                onCheckedChange = { onToggleTheme() }
                            )
                        }

                        // Renderizado condicional del área privada según estado de autenticación (isLoggedIn)
                        if (isLoggedIn) {
                            NavigationDrawerItem(
                                icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Mensajes") },
                                label = { Text("Mensajes") },
                                selected = currentRoute == Screen.ListaContactos.route,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    navegarA(Screen.ListaContactos.route)
                                },
                                modifier = Modifier.padding(vertical = 2.dp),
                                shape = MaterialTheme.shapes.medium
                            )

                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                        contentDescription = "Cerrar Sesión",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                },
                                label = { Text("Cerrar Sesión", color = MaterialTheme.colorScheme.error) },
                                selected = false,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    authViewModel.cerrarSesion()
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                                    }
                                },
                                modifier = Modifier.padding(vertical = 2.dp),
                                shape = MaterialTheme.shapes.medium
                            )
                        } else {
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Default.Lock, contentDescription = "Acceso Funcionarios") },
                                label = { Text("Acceso Funcionarios") },
                                selected = currentRoute == Screen.Login.route,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    navegarA(Screen.Login.route)
                                },
                                modifier = Modifier.padding(vertical = 2.dp),
                                shape = MaterialTheme.shapes.medium
                            )
                        }
                    }
                }
            }
        }
    ) {
        // Estructura Scaffold con TopAppBar y NavigationBar inferiores en pantallas móviles
        Scaffold(
            topBar = {
                if (mostrarNavbar && isCompact) {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Abrir Menú",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        title = {
                            Text(
                                text = "Alba Lab",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
            },
            bottomBar = {
                if (mostrarNavbar && isCompact) {
                    NavigationBar {
                        bottomNavItems.forEach { (route, label, icon) ->
                            NavigationBarItem(
                                icon = { Icon(icon, contentDescription = label) },
                                label = { Text(label) },
                                selected = currentRoute == route,
                                onClick = { navegarA(route) }
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
                // Layout adaptativo: Muestra panel lateral expansible con animación Spring en tablets/pantallas grandes
                if (mostrarNavbar && !isCompact) {
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
                            .width(menuWidth)
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
                            // Cabecera interactiva del panel colapsable
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = if (isMenuExpanded) Arrangement.SpaceBetween else Arrangement.Center
                            ) {
                                AnimatedVisibility(
                                    visible = isMenuExpanded,
                                    enter = fadeIn(animationSpec = tween(200)),
                                    exit = fadeOut(animationSpec = tween(100))
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = MaterialTheme.colorScheme.surface,
                                            tonalElevation = 1.dp
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.logo_alba_lab),
                                                contentDescription = "Logo Alba Lab",
                                                contentScale = ContentScale.Fit,
                                                modifier = Modifier
                                                    .height(32.dp)
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
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

                            AnimatedVisibility(
                                visible = isMenuExpanded,
                                enter = fadeIn(animationSpec = tween(250)),
                                exit = fadeOut(animationSpec = tween(100))
                            ) {
                                Column {
                                    HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))

                                    // 1. Secciones de navegación principal
                                    publicMenuItemsExpanded.forEach { (route, label, icon) ->
                                        NavigationDrawerItem(
                                            icon = { Icon(icon, contentDescription = label) },
                                            label = { Text(label) },
                                            selected = currentRoute == route,
                                            onClick = { navegarA(route) },
                                            modifier = Modifier.padding(horizontal = 4.dp),
                                            shape = MaterialTheme.shapes.medium
                                        )
                                    }

                                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                                    // 2. Control de Tema (Modo Oscuro)
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 12.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                                                contentDescription = "Tema Oscuro",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Text(
                                                text = "Modo Oscuro",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                        Switch(
                                            checked = isDarkTheme,
                                            onCheckedChange = { onToggleTheme() }
                                        )
                                    }

                                    // 3. Acceso a Funcionarios / Administración de sesión debajo de Modo Oscuro
                                    if (isLoggedIn) {
                                        NavigationDrawerItem(
                                            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Mensajes") },
                                            label = { Text("Mensajes") },
                                            selected = currentRoute == Screen.ListaContactos.route,
                                            onClick = { navegarA(Screen.ListaContactos.route) },
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                            shape = MaterialTheme.shapes.medium
                                        )

                                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                                        NavigationDrawerItem(
                                            icon = {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                                    contentDescription = "Cerrar Sesión",
                                                    tint = MaterialTheme.colorScheme.error
                                                )
                                            },
                                            label = { Text("Cerrar Sesión", color = MaterialTheme.colorScheme.error) },
                                            selected = false,
                                            onClick = {
                                                authViewModel.cerrarSesion()
                                                navController.navigate(Screen.Home.route) {
                                                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                                                }
                                            },
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                            shape = MaterialTheme.shapes.medium
                                        )
                                    } else {
                                        NavigationDrawerItem(
                                            icon = { Icon(Icons.Default.Lock, contentDescription = "Acceso Funcionarios") },
                                            label = { Text("Acceso Funcionarios") },
                                            selected = currentRoute == Screen.Login.route,
                                            onClick = { navegarA(Screen.Login.route) },
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                            shape = MaterialTheme.shapes.medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // NavHost: Define el grafo de pantallas y transiciones animadas al cambiar de ruta
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
                    // Ruta: Pantalla de Inicio (Home)
                    composable(route = Screen.Home.route) {
                        HomeScreen(navController = navController, viewModel = mainViewModel)
                    }
                    // Ruta: Ley de Inclusión
                    composable(route = Screen.LeyInclusion.route) {
                        LeyInclusionScreen(
                            onContactarClick = { navegarA(Screen.Contacto.route) }
                        )
                    }
                    // Ruta: Quiénes Somos
                    composable(route = Screen.QuienesSomos.route){
                        QuienesSomosScreen(
                            onContactoClick = { navegarA(Screen.Contacto.route) }
                        )
                    }
                    // Ruta: Qué Hacemos
                    composable(route = Screen.QueHacemos.route){
                        QueHacemosScreen(
                            onContactoClick = { navegarA(Screen.Contacto.route) }
                        )
                    }
                    // Ruta: Formulario de Contacto (Construye dinámicamente el ViewModel inyectando el Repositorio)
                    composable(route = Screen.Contacto.route) {
                        val context = LocalContext.current
                        val database = AppDatabase.getDatabase(context)
                        val repository = ContactoRepository(database.contactoDao())

                        val contactoViewModel: ContactoViewModel = viewModel(
                            factory = object : ViewModelProvider.Factory {
                                @Suppress("UNCHECKED_CAST")
                                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                    return ContactoViewModel(repository) as T
                                }
                            }
                        )

                        ContactoScreen(viewModel = contactoViewModel)
                    }
                    // Ruta: Inicio de Sesión de Funcionarios (Login)
                    composable(route = Screen.Login.route) {
                        LoginScreen(
                            authViewModel = authViewModel,
                            onLoginSuccess = {
                                navController.navigate(Screen.ListaContactos.route) {
                                    popUpTo(Screen.Home.route)
                                }
                            }
                        )
                    }
                    // Ruta: Panel Privado de Mensajes (Protegido por autenticación)
                    composable(route = Screen.ListaContactos.route) {
                        if (isLoggedIn) {
                            val context = LocalContext.current
                            val database = AppDatabase.getDatabase(context)
                            val repository = ContactoRepository(database.contactoDao())

                            val contactoViewModel: ContactoViewModel = viewModel(
                                factory = object : ViewModelProvider.Factory {
                                    @Suppress("UNCHECKED_CAST")
                                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                        return ContactoViewModel(repository) as T
                                    }
                                }
                            )

                            ListaContactosScreen(viewModel = contactoViewModel)
                        } else {
                            // Redirecciona al Login si intenta ingresar sin estar autenticado
                            LoginScreen(
                                authViewModel = authViewModel,
                                onLoginSuccess = {
                                    navController.navigate(Screen.ListaContactos.route) {
                                        popUpTo(Screen.Home.route)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * FUNCIÓN UTILITARIA AUXILIAR DE DISEÑO RESPONSIVO
 *
 * Determina en tiempo real si el ancho disponible de pantalla corresponde a un dispositivo compacto.
 *
 * @return `true` si la pantalla tiene un ancho inferior a 600 dp (Smartphones),
 *         `false` para pantallas medianas o anchas (Tablets/Desktops).
 */
@Composable
private fun esPantallaCompacta(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp < 600
}