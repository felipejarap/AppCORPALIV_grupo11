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
import com.example.appcorpaliv_grupo11.ui.components.BannerEstadoRed
import com.example.appcorpaliv_grupo11.ui.components.DialogoEditarContacto
import com.example.appcorpaliv_grupo11.ui.components.TarjetaMensajeItem
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

@Composable
fun ListaContactosCompacta(viewModel: ContactoViewModel) {
    val listaContactos by viewModel.listaContactos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val filtroActual = viewModel.filtroActual
    val filtros = listOf("Todos", "Pendientes", "Contestados", "Rechazados")

    var contactoAEditar by remember { mutableStateOf<ContactoEntity?>(null) }
    var contactoAEliminar by remember { mutableStateOf<ContactoEntity?>(null) }

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

        // Aviso seguro de red / servicio no disponible con botón de reintento
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(12.dp))
            BannerEstadoRed(
                mensaje = errorMessage!!,
                onRetry = { viewModel.sincronizarContactos() }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading && mensajesFiltrados.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            mensajesFiltrados.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (errorMessage != null) {
                            "No se pudieron cargar los mensajes."
                        } else {
                            "No hay mensajes en '$filtroActual'."
                        },
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = mensajesFiltrados,
                        key = { item: ContactoEntity -> "${item.id}_${item.email}_${item.mensaje.hashCode()}" }
                    ) { item ->
                        TarjetaMensajeItem(
                            contacto = item,
                            onCambiarEstado = { contacto, nuevoEstado ->
                                viewModel.cambiarEstadoContacto(contacto, nuevoEstado)
                            },
                            onEditar = { contacto -> contactoAEditar = contacto },
                            onEliminar = { contacto -> contactoAEliminar = contacto }
                        )
                    }
                }
            }
        }
    }

    // Pop-up de edición (PUT)
    contactoAEditar?.let { contacto ->
        DialogoEditarContacto(
            contacto = contacto,
            opcionesAsunto = viewModel.opcionesAsunto,
            onDismiss = { contactoAEditar = null },
            onGuardar = { contactoActualizado ->
                viewModel.actualizarContacto(contactoActualizado)
                contactoAEditar = null
            }
        )
    }

    // Pop-up de confirmación de borrado (DELETE)
    contactoAEliminar?.let { contacto ->
        AlertDialog(
            onDismissRequest = { contactoAEliminar = null },
            title = { Text("Eliminar mensaje") },
            text = { Text("¿Seguro que deseas eliminar el mensaje de ${contacto.nombre}?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.eliminarContacto(contacto)
                        contactoAEliminar = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { contactoAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}