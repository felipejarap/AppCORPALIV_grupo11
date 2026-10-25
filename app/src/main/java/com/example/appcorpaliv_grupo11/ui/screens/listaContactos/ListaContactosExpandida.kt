package com.example.appcorpaliv_grupo11.ui.screens.listaContactos

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

@Composable
fun ListaContactosExpandida(viewModel: ContactoViewModel) {
    val todosContactos by viewModel.listaContactos.collectAsStateWithLifecycle()
    val filtro = viewModel.filtroActual
    var seleccionado by remember { mutableStateOf<ContactoEntity?>(null) }

    val contactosFiltrados = remember(todosContactos, filtro) {
        when (filtro) {
            "Pendientes" -> todosContactos.filter { it.estado == "Pendiente" }
            "Contestados" -> todosContactos.filter { it.estado == "Contestado" }
            "Rechazados" -> todosContactos.filter { it.estado == "Rechazado" }
            else -> todosContactos
        }
    }

    LaunchedEffect(contactosFiltrados) {
        seleccionado?.let { actual ->
            seleccionado = contactosFiltrados.find { it.id == actual.id }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Columna Izquierda: Lista Maestro con Filtros Desplazables
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Mensajes (${contactosFiltrados.size})",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Fila con scroll horizontal para que los chips NUNCA se deformen ni rompan el texto
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
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

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(contactosFiltrados, key = { it.id }) { contacto ->
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = contacto.nombre,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = "${contacto.email} • ${contacto.estado}",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { seleccionado = contacto },
                            colors = ListItemDefaults.colors(
                                containerColor = if (seleccionado?.id == contacto.id)
                                    MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                }
            }
        }

        // Columna Derecha: Panel Detalle (Sin botón de eliminar)
        Card(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                val item = seleccionado
                if (item != null) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.nombre,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            AssistChip(
                                onClick = { },
                                label = { Text(item.estado, fontWeight = FontWeight.SemiBold) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = when (item.estado) {
                                        "Contestado" -> MaterialTheme.colorScheme.primaryContainer
                                        "Rechazado" -> MaterialTheme.colorScheme.errorContainer
                                        else -> MaterialTheme.colorScheme.tertiaryContainer
                                    }
                                )
                            )
                        }

                        Text(
                            text = item.email,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        if (item.asunto.isNotBlank()) {
                            Text(text = "Asunto: ${item.asunto}", style = MaterialTheme.typography.titleSmall)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = item.mensaje, style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.weight(1f))

                        // Botones de acción alineados a la derecha (Sin botón de eliminar)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (item.estado != "Rechazado") {
                                OutlinedButton(
                                    onClick = { viewModel.cambiarEstadoContacto(item, "Rechazado") },
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Rechazar", maxLines = 1)
                                }
                            }

                            if (item.estado != "Contestado" && item.estado != "Rechazado") {
                                Spacer(modifier = Modifier.width(12.dp))
                            }

                            if (item.estado != "Contestado") {
                                Button(onClick = { viewModel.cambiarEstadoContacto(item, "Contestado") }) {
                                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Contestar", maxLines = 1)
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "Selecciona un contacto para revisar el mensaje.",
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}