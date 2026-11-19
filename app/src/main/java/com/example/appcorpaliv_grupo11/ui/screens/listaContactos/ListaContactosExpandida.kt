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

/**
 * PANTALLA DE LISTA DE CONTACTOS EN FORMATO EXPANDIDO (TABLETS EN LANDSCAPE / MONITORES)
 *
 * Sub-vista adaptativa diseñada para pantallas de gran formato (ancho superior a 840 dp).
 * Reorganiza el panel de administración con márgenes amplios y tipografías de gran jerarquía:
 * - Encabezado principal en estilo MaterialTheme.typography.headlineLarge.
 * - Barra horizontal de filtros dinámicos mediante FilterChip en LazyRow.
 * - Banner seguro de estado de red con opción de reintento ante caídas del backend.
 * - Filtrado eficiente en memoria con remember recalculado automáticamente ante cambios en el StateFlow.
 * - Listado vertical optimizado (LazyColumn) con tarjetas TarjetaMensajeItem y separación amplia de 16 dp.
 *
 * @param viewModel Instancia de [ContactoViewModel] para administrar la consulta, edición y eliminación de registros.
 */
@Composable
fun ListaContactosExpandida(viewModel: ContactoViewModel) {
    // Suscripción reactiva a los StateFlows expuestos por el ViewModel
    val listaContactos by viewModel.listaContactos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Estado del filtro de búsqueda seleccionado
    val filtroActual = viewModel.filtroActual
    val filtros = listOf("Todos", "Pendientes", "Contestados", "Rechazados")

    // Estados para los pop-ups de edición (PUT) y eliminación (DELETE)
    var contactoAEditar by remember { mutableStateOf<ContactoEntity?>(null) }
    var contactoAEliminar by remember { mutableStateOf<ContactoEntity?>(null) }

    // Cálculo recordado del filtrado para evitar ejecuciones innecesarias durante las recomposiciones
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
            .padding(24.dp)
    ) {
        // =========================================================================
        // 1. ENCABEZADO DESTACADO DEL PANEL DE ADMINISTRACIÓN
        // =========================================================================
        Text(
            text = "Gestión de Mensajes",
            style = MaterialTheme.typography.headlineLarge,
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

        // Aviso seguro de red / servicio no disponible con botón de reintento
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(12.dp))
            BannerEstadoRed(
                mensaje = errorMessage!!,
                onRetry = { viewModel.sincronizarContactos() }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // =========================================================================
        // 3. RENDERIZADO DE LA LISTA, CARGA O ESTADO VACÍO (LAZYCOLUMN)
        // =========================================================================
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
                    verticalArrangement = Arrangement.spacedBy(16.dp),
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

    // Pop-up de edición (PUT) reutilizando el componente compartido
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