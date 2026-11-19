package com.example.appcorpaliv_grupo11.ui.screens.listaContactos

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

/**
 * PANTALLA PRINCIPAL DE LISTA DE CONTACTOS / PANEL ADMINISTRATIVO (ADAPTIVE ROOT)
 *
 * Contenedor adaptativo raíz de la sección de visualización y gestión de mensajes recibidos.
 * Evalúa dinámicamente la categoría de ancho de ventana activos (WindowWidthSizeClass)
 * y delega la distribución del listado a la sub-vista correspondiente
 * (ListaContactosCompacta, ListaContactosMediana o ListaContactosExpandida),
 * inyectando la instancia del ViewModel de contacto.
 *
 * @param windowSizeClass Clasificación del ancho de pantalla (calculada dinámicamente si no se provee).
 * @param viewModel Instancia de [ContactoViewModel] que gestiona la consulta y eliminación de mensajes en la base de datos Room.
 */
@Composable
fun ListaContactosScreen(
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    viewModel: ContactoViewModel = viewModel()
) {
    // Selección declarativa de la sub-vista según la categoría de ancho del dispositivo
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> ListaContactosCompacta(viewModel)
        WindowWidthSizeClass.Medium -> ListaContactosMediana(viewModel)
        WindowWidthSizeClass.Expanded -> ListaContactosExpandida(viewModel)
        else -> ListaContactosCompacta(viewModel)
    }
}

/**
 * CÁLCULO DINÁMICO DE CLASIFICACIÓN DE ANCHO DE VENTANA
 *
 * Determina la categoría de ancho WindowWidthSizeClass analizando los dp disponibles
 * en la configuración local mediante LocalConfiguration.current.
 *
 * Puntos de quiebre (Breakpoints) estándar de Material Design 3:
 * - < 600 dp: Compact (Smartphones portrait)
 * - 600 dp a 839 dp: Medium (Tablets portrait / Smartphones landscape)
 * - >= 840 dp: Expanded (Tablets landscape / Monitores / Desktops)
 */
@Composable
private fun obtenerWindowWidthSizeClass(): WindowWidthSizeClass {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 600 -> WindowWidthSizeClass.Compact
        configuration.screenWidthDp < 840 -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }
}