package com.example.appcorpaliv_grupo11.ui.components

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.appcorpaliv_grupo11.model.ContactoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogoEditarContacto(
    contacto: ContactoEntity,
    opcionesAsunto: List<String>,
    onDismiss: () -> Unit,
    onGuardar: (ContactoEntity) -> Unit
) {
    var nombre by remember { mutableStateOf(contacto.nombre) }
    var email by remember { mutableStateOf(contacto.email) }
    var telefono by remember { mutableStateOf(contacto.telefono) }
    var asunto by remember { mutableStateOf(contacto.asunto) }
    var mensaje by remember { mutableStateOf(contacto.mensaje) }
    var comboExpandido by remember { mutableStateOf(false) }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var telefonoError by remember { mutableStateOf<String?>(null) }
    var asuntoError by remember { mutableStateOf<String?>(null) }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    fun validarEdicion(): Boolean {
        var esValido = true

        if (nombre.trim().isBlank()) {
            nombreError = "El nombre es obligatorio *"
            esValido = false
        }

        if (email.trim().isBlank()) {
            emailError = "El correo electrónico es obligatorio *"
            esValido = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            emailError = "Ingresa un correo electrónico válido."
            esValido = false
        }

        if (telefono.isBlank()) {
            telefonoError = "El teléfono es obligatorio *"
            esValido = false
        } else if (telefono.length != 9 || !telefono.startsWith("9")) {
            telefonoError = "Debe ingresar 9 dígitos comenzando con 9 (Ej: 912345678)."
            esValido = false
        }

        if (asunto.trim().isBlank()) {
            asuntoError = "Por favor selecciona un asunto *"
            esValido = false
        }

        if (mensaje.trim().isBlank()) {
            mensajeError = "El mensaje no puede estar vacío *"
            esValido = false
        }

        return esValido
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar mensaje") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        nombreError = null
                    },
                    label = { Text("Nombre") },
                    singleLine = true,
                    isError = nombreError != null,
                    supportingText = nombreError?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    },
                    label = { Text("Correo") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = emailError != null,
                    supportingText = emailError?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = telefono,
                    onValueChange = { nuevo ->
                        telefono = nuevo.filter { it.isDigit() }.take(9)
                        telefonoError = null
                    },
                    label = { Text("Teléfono") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = telefonoError != null,
                    supportingText = telefonoError?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = comboExpandido,
                    onExpandedChange = { comboExpandido = !comboExpandido },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = asunto,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Asunto") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = comboExpandido)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        isError = asuntoError != null,
                        supportingText = asuntoError?.let { { Text(it) } },
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = comboExpandido,
                        onDismissRequest = { comboExpandido = false }
                    ) {
                        opcionesAsunto.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    asunto = opcion
                                    asuntoError = null
                                    comboExpandido = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = mensaje,
                    onValueChange = {
                        mensaje = it
                        mensajeError = null
                    },
                    label = { Text("Mensaje") },
                    isError = mensajeError != null,
                    supportingText = mensajeError?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (validarEdicion()) {
                        onGuardar(
                            contacto.copy(
                                nombre = nombre.trim(),
                                email = email.trim(),
                                telefono = telefono.trim(),
                                asunto = asunto.trim(),
                                mensaje = mensaje.trim()
                            )
                        )
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}