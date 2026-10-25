package com.example.appcorpaliv_grupo11.ui.screens.listaContactos

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

@Composable
fun ListaContactosCompacta(viewModel: ContactoViewModel) {
    val todosContactos by viewModel.listaContactos.collectAsStateWithLifecycle()
    val filtro = viewModel.filtroActual

    // Filtrado local según la pestaña activa
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

        // Fila de Filtros con scroll horizontal para evitar que "Rechazados" se deforme
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
            LazyColumn(
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

@Composable
fun ContactoCardItem(
    contacto: ContactoEntity,
    onEstadoChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Encabezado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = contacto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                AssistChip(
                    onClick = { },
                    label = { Text(contacto.estado, fontWeight = FontWeight.SemiBold) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = when (contacto.estado) {
                            "Contestado" -> MaterialTheme.colorScheme.primaryContainer
                            "Rechazado" -> MaterialTheme.colorScheme.errorContainer
                            else -> MaterialTheme.colorScheme.tertiaryContainer
                        }
                    )
                )
            }

            Text(
                text = contacto.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            if (contacto.asunto.isNotBlank()) {
                Text(
                    text = "Asunto: ${contacto.asunto}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(text = contacto.mensaje, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(12.dp))

            // Botones de acción alineados a la derecha (Sin botón de basurero)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (contacto.estado != "Rechazado") {
                    OutlinedButton(
                        onClick = { onEstadoChange("Rechazado") },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Rechazar", maxLines = 1)
                    }
                }

                if (contacto.estado != "Contestado" && contacto.estado != "Rechazado") {
                    Spacer(modifier = Modifier.width(8.dp))
                }

                if (contacto.estado != "Contestado") {
                    Button(onClick = { onEstadoChange("Contestado") }) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Contestar", maxLines = 1)
                    }
                }
            }
        }
    }
}