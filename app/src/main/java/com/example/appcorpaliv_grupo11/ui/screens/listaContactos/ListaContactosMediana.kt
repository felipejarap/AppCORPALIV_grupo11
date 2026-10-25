package com.example.appcorpaliv_grupo11.ui.screens.listaContactos

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

@Composable
fun ListaContactosMediana(viewModel: ContactoViewModel) {
    val todosContactos by viewModel.listaContactos.collectAsStateWithLifecycle()
    val filtro = viewModel.filtroActual

    val contactosFiltrados = remember(todosContactos, filtro) {
        when (filtro) {
            "Pendientes" -> todosContactos.filter { it.estado == "Pendiente" }
            "Contestados" -> todosContactos.filter { it.estado == "Contestado" }
            "Rechazados" -> todosContactos.filter { it.estado == "Rechazado" }
            else -> todosContactos
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Gestión de Mensajes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Fila de Filtros con scroll horizontal para evitar deformación de texto
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            val opciones = listOf("Todos", "Pendientes", "Contestados", "Rechazados")
            opciones.forEach { opcion ->
                FilterChip(
                    selected = filtro == opcion,
                    onClick = { viewModel.cambiarFiltro(opcion) },
                    label = { Text(opcion, maxLines = 1) }
                )
            }
        }

        if (contactosFiltrados.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No hay registros en la categoría '$filtro'.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(contactosFiltrados, key = { it.id }) { contacto ->
                    ContactoCardItem(
                        contacto = contacto,
                        onEstadoChange = { nuevoEstado ->
                            viewModel.cambiarEstadoContacto(contacto, nuevoEstado)
                        }
                    )
                }
            }
        }
    }
}