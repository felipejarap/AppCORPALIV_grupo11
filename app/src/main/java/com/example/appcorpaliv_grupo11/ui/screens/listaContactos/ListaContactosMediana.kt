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
 * PANTALLA DE LISTA DE CONTACTOS EN FORMATO MEDIANO (TABLETS PORTRAIT / SMARTPHONES LANDSCAPE)
 *
 * Sub-vista adaptativa diseñada para dispositivos de formato intermedio (ancho entre 600 dp y 839 dp).
 * Reorganiza el panel administrativo de mensajes manteniendo proporciones equilibradas:
 * 1. Encabezado principal en estilo MaterialTheme.typography.headlineMedium.
 * 2. Barra horizontal de filtros dinámicos mediante FilterChip en LazyRow.
 * 3. Filtrado eficiente en memoria mediante remember recalculado ante cambios en el StateFlow de Room.
 * 4. Estado vacío centrado cuando la consulta no retorne coincidencias.
 * 5. Listado vertical optimizado (LazyColumn) con tarjetas TarjetaMensajeItem y separación de 12 dp.
 *
 * @param viewModel Instancia de [ContactoViewModel] para administrar la lectura y actualización de estados en Room.
 */
@Composable
fun ListaContactosMediana(viewModel: ContactoViewModel) {
    // Suscripción reactiva al StateFlow del ViewModel para sincronizar cambios persistidos en Room
    val listaContactos by viewModel.listaContactos.collectAsState()

    // Estado del filtro de búsqueda seleccionado
    val filtroActual = viewModel.filtroActual
    val filtros = listOf("Todos", "Pendientes", "Contestados", "Rechazados")

    // Cálculo optimizado del filtrado en memoria reactivo
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
        // 1. ENCABEZADO PRINCIPAL DEL PANEL DE ADMINISTRACIÓN
        // =========================================================================
        Text(
            text = "Gestión de Mensajes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        // =========================================================================
        // 2. BARRA DE CHIPS PARA FILTRADO POR ESTADO (LAZYROW)
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
        // 3. RENDERIZADO DE LA LISTA O ESTADO VACÍO (LAZYCOLUMN)
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