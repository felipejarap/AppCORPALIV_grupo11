package com.example.appcorpaliv_grupo11.ui.screens.listaContactos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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

@Composable
fun ListaContactosExpandida(viewModel: ContactoViewModel) {
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
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Text(
            text = "Centro de Gestión de Mensajes",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(filtros) { filtro ->
                FilterChip(
                    selected = filtroActual == filtro,
                    onClick = { viewModel.cambiarFiltro(filtro) },
                    label = { Text(filtro) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (mensajesFiltrados.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay mensajes en '$filtroActual'.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 420.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = mensajesFiltrados,
                    key = { item: ContactoEntity -> item.id }
                ) { item ->
                    TarjetaMensajeItem(
                        contacto = item,
                        onRechazarClick = { viewModel.cambiarEstadoContacto(item, "Rechazado") },
                        onContestarClick = { viewModel.cambiarEstadoContacto(item, "Contestado") }
                    )
                }
            }
        }
    }
}