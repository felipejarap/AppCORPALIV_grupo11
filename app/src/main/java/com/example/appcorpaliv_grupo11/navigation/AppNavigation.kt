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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
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
import com.example.appcorpaliv_grupo11.ui.screens.PantallaPrincipal
import com.example.appcorpaliv_grupo11.ui.screens.ProfileScreen
import com.example.appcorpaliv_grupo11.ui.screens.RegistroScreen
import com.example.appcorpaliv_grupo11.ui.screens.ResumenScreen
import com.example.appcorpaliv_grupo11.ui.screens.SettingsScreen
import com.example.appcorpaliv_grupo11.ui.screens.auth.LoginScreen
import com.example.appcorpaliv_grupo11.ui.screens.contacto.ContactoScreen
import com.example.appcorpaliv_grupo11.ui.screens.listaContactos.ListaContactosScreen
import com.example.appcorpaliv_grupo11.viewModel.AuthViewModel
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel
import com.example.appcorpaliv_grupo11.viewModel.MainViewModel
import com.example.appcorpaliv_grupo11.viewModel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    isDarkTheme: Boolean = false,
    onToggleTheme: () -> Unit = {}
) {
    val navController = rememberNavController()
    val isCompact = esPantallaCompacta()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isMenuExpanded by remember { mutableStateOf(false) }

    val usuarioViewModel: UsuarioViewModel = viewModel()
    val mainViewModel: MainViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()

    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val mostrarNavbar = currentRoute == null || currentRoute in listOf(
        Screen.Home.route,
        Screen.Profile.route,
        Screen.Settings.route,
        Screen.Registro.route,
        Screen.Resumen.route,
        Screen.Guia12.route,
        Screen.Contacto.route,
        Screen.ListaContactos.route,
        Screen.Login.route
    )

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

    val baseMenuItems = listOf(
        Triple(Screen.Home.route, "Home", Icons.Default.Home),
        Triple(Screen.Registro.route, "Registro", Icons.AutoMirrored.Filled.List),
        Triple(Screen.Profile.route, "Perfil", Icons.Default.Person),
        Triple(Screen.Settings.route, "Ajustes", Icons.Default.Settings),
        Triple(Screen.Guia12.route, "Animación", Icons.Default.Star),
        Triple(Screen.Contacto.route, "Contacto", Icons.Default.Email)
    )

    val menuItems = if (isLoggedIn) {
        baseMenuItems + Triple(Screen.ListaContactos.route, "Mensajes", Icons.AutoMirrored.Filled.List)
    } else {
        baseMenuItems + Triple(Screen.Login.route, "Acceso Funcionarios", Icons.Default.Lock)
    }

    val essentialMenuItems = if (isLoggedIn) {
        listOf(
            Triple(Screen.Home.route, "Home", Icons.Default.Home),
            Triple(Screen.Contacto.route, "Contacto", Icons.Default.Email),
            Triple(Screen.ListaContactos.route, "Mensajes", Icons.AutoMirrored.Filled.List)
        )
    } else {
        listOf(
            Triple(Screen.Home.route, "Home", Icons.Default.Home),
            Triple(Screen.Contacto.route, "Contacto", Icons.Default.Email)
        )
    }

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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_alba_lab),
                                contentDescription = "Logo Alba Lab",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .height(40.dp)
                                    .wrapContentWidth()
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Alba Lab",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        HorizontalDivider(modifier = Modifier.padding(bottom = 12.dp))

                        menuItems.forEach { (route, label, icon) ->
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

                        if (isLoggedIn) {
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
                                    scope.launch { drawerState.close() }
                                    authViewModel.cerrarSesion()
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                                    }
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
                        actions = {
                            IconButton(onClick = onToggleTheme) {
                                Icon(
                                    imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                    contentDescription = "Cambiar tema",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
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
                        essentialMenuItems.forEach { (route, label, icon) ->
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
                            // Cabecera del Menú Lateral
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

                            // Botón de Modo Oscuro/Claro (siempre accesible en pantallas no compactas)
                            IconButton(
                                onClick = onToggleTheme,
                                modifier = Modifier.padding(vertical = 2.dp)
                            ) {
                                Icon(
                                    imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                    contentDescription = "Cambiar tema",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

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
                                            onClick = { navegarA(route) },
                                            modifier = Modifier.padding(horizontal = 4.dp),
                                            shape = MaterialTheme.shapes.medium
                                        )
                                    }

                                    if (isLoggedIn) {
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
                                            modifier = Modifier.padding(horizontal = 4.dp),
                                            shape = MaterialTheme.shapes.medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

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

@Composable
private fun esPantallaCompacta(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp < 600
}