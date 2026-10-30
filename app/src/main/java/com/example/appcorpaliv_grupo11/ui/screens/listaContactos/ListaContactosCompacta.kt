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
 * Vista para dispositivos de tamaño compacto (< 600 dp).
 * Renderiza los mensajes en 1 sola columna vertical con filtrado dinámico y eliminación.
 *
 * @param viewModel Instancia de [ContactoViewModel] para administrar los datos y filtros.
 */
@Composable
fun ListaContactosCompacta(viewModel: ContactoViewModel) {
    val listaContactos by viewModel.listaContactos.collectAsState()
    val filtroActual = viewModel.filtroActual
    val filtros = listOf("Todos", "Pendientes", "Contestados", "Rechazados")

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
        Text(
            text = "Gestión de Mensajes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

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