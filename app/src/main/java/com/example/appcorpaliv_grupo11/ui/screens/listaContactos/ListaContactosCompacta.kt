package com.example.appcorpaliv_grupo11.ui.screens.listaContactos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.example.appcorpaliv_grupo11.model.ContactoEntity
import com.example.appcorpaliv_grupo11.ui.components.TarjetaMensajeItem
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

/**
 * PANTALLA DE LISTA DE CONTACTOS EN FORMATO COMPACTO (SMARTPHONES PORTRAIT)
 *
 * Sub-vista adaptativa diseñada para dispositivos móviles con ancho de pantalla menor a 600 dp.
 * Renderiza el listado de solicitudes recibidas en una sola columna vertical optimizada ([LazyColumn]):
 * 1. Encabezado principal del panel de administración.
 * 2. Barra deslizable de chips de filtrado ([FilterChip] en [LazyRow]) por estado ("Todos", "Pendientes", "Contestados", "Rechazados").
 * 3. Filtrado eficiente en memoria recalculado reactivamente mediante [remember].
 * 4. Estado vacío informativo cuando la lista filtrada no arroje coincidencias.
 * 5. Tarjetas interactivas [TarjetaMensajeItem] para gestionar el estado de los registros en la base de datos Room.
 *
 * @param viewModel Instancia de [ContactoViewModel] para administrar la lectura y actualización de los estados.
 */
@Composable
fun ListaContactosCompacta(viewModel: ContactoViewModel) {
    // Observación reactiva de la lista completa de contactos desde el StateFlow del ViewModel
    val listaContactos by viewModel.listaContactos.collectAsState()

    // Filtro actualmente seleccionado en la interfaz
    val filtroActual = viewModel.filtroActual
    val filtros = listOf("Todos", "Pendientes", "Contestados", "Rechazados")

    // Cálculo optimizado del filtrado en memoria reactivo para evitar recomputaciones innecesarias
    val mensajesFiltrados = remember(filtroActual, listaContactos) {
        when (filtroActual) {
            "Pendientes" -> listaContactos.filter { it.estado.equals("Pendiente", ignoreCase = true) }
            "Contestados" -> listaContactos.filter { it.estado.equals("Contestado", ignoreCase = true) }
            "Rechazados" -> listaContactos.filter { it.estado.equals("Rechazado", ignoreCase = true) }
            else -> listaContactos
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // =========================================================================
        // 1. ENCABEZADO DE LA SECCIÓN ADMINISTRATIVA
        // =========================================================================
        Text(
            text = "Gestión de Mensajes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        // =========================================================================
        // 2. BARRA HORIZONTAL DE FILTROS MEDIANTE CHIPS (LAZYROW)
        // =========================================================================
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filtros) { filtro ->
                FilterChip(
                    selected = filtroActual == filtro,
                    onClick = { viewModel.cambiarFiltro(filtro) },
                    label = { Text(filtro) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // =========================================================================
        // 3. RENDERIZADO CONDICIONAL: ESTADO VACÍO VS LISTA DE MENSAJES (LAZYCOLUMN)
        // =========================================================================
        if (mensajesFiltrados.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay mensajes en '$filtroActual'.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = mensajesFiltrados,
                    key = { item: ContactoEntity -> item.id }
                ) { item ->
                    TarjetaMensajeItem(
                        contacto = item,
                        onCambiarEstado = { contacto, nuevoEstado ->
                            viewModel.cambiarEstadoContacto(contacto, nuevoEstado)
                        }
                    )
                }
            }
        }
    }
}