package com.example.appcorpaliv_grupo11.ui.screens.listaContactos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

@Composable
fun ListaContactosExpandida(viewModel: ContactoViewModel) {
    val contactos by viewModel.listaContactos.collectAsStateWithLifecycle()
    var seleccionado by remember { mutableStateOf<ContactoEntity?>(null) }
    var mostrarDialogoEditar by remember { mutableStateOf(false) }

    LaunchedEffect(contactos) {
        seleccionado?.let { actual ->
            seleccionado = contactos.find { it.id == actual.id }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Columna Izquierda: Lista Maestro
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Contactos (${contactos.size})",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(contactos, key = { it.id }) { contacto ->
                        ListItem(
                            headlineContent = { Text(contacto.nombre, fontWeight = FontWeight.Bold) },
                            supportingContent = { Text(contacto.email) },
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

        // Columna Derecha: Panel Detalle
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
                        Text(text = item.nombre, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Text(text = item.email, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(16.dp))
                        if (item.asunto.isNotBlank()) {
                            Text(text = "Asunto: ${item.asunto}", style = MaterialTheme.typography.titleSmall)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = item.mensaje, style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.weight(1f))

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(onClick = { mostrarDialogoEditar = true }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar")
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Editar")
                            }

                            Button(
                                onClick = {
                                    viewModel.eliminarContacto(item)
                                    seleccionado = null
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Eliminar Registro")
                            }
                        }
                    }
                } else {
                    Text(
                        text = "Selecciona un contacto de la lista para ver el detalle.",
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

    // Modal con alto delimitado para habilitar scroll en landscape
    if (mostrarDialogoEditar && seleccionado != null) {
        val itemAEditar = seleccionado!!
        var nombreEdit by remember(itemAEditar) { mutableStateOf(itemAEditar.nombre) }
        var emailEdit by remember(itemAEditar) { mutableStateOf(itemAEditar.email) }
        var asuntoEdit by remember(itemAEditar) { mutableStateOf(itemAEditar.asunto) }
        var mensajeEdit by remember(itemAEditar) { mutableStateOf(itemAEditar.mensaje) }

        Dialog(
            onDismissRequest = { mostrarDialogoEditar = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.85f) // CLAVE: Restringe la altura para activar el scroll
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(28.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Editar Contacto",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = nombreEdit,
                        onValueChange = { nombreEdit = it },
                        label = { Text("Nombre") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = emailEdit,
                        onValueChange = { emailEdit = it },
                        label = { Text("Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = asuntoEdit,
                        onValueChange = { asuntoEdit = it },
                        label = { Text("Asunto") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = mensajeEdit,
                        onValueChange = { mensajeEdit = it },
                        label = { Text("Mensaje") },
                        maxLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { mostrarDialogoEditar = false }) {
                            Text("Cancelar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                val contactoActualizado = itemAEditar.copy(
                                    nombre = nombreEdit,
                                    email = emailEdit,
                                    asunto = asuntoEdit,
                                    mensaje = mensajeEdit
                                )
                                viewModel.actualizarContacto(contactoActualizado)
                                seleccionado = contactoActualizado
                                mostrarDialogoEditar = false
                            }
                        ) {
                            Text("Guardar Cambios")
                        }
                    }
                }
            }
        }
    }
}