package com.example.appcorpaliv_grupo11.ui.screens.contacto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import com.example.appcorpaliv_grupo11.ui.components.*
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

@Composable
fun ContactoScreenCompacta(viewModel: ContactoViewModel) {
    val scrollState = rememberScrollState()
    val isSubmitted by viewModel.isSubmitted.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState() // <-- Observamos el estado de carga

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        // 1. Tarjeta superior de información
        TarjetaInfoContacto(
            modifier = Modifier.padding(16.dp)
        )

        // 2. Formulario / Éxito
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            TarjetaExitoAnimada(
                mostrarExito = isSubmitted,
                onEnviarOtroMensaje = { viewModel.reiniciarFormulario() }
            )

            if (!isSubmitted) {
                CampoTextoInput(
                    value = viewModel.nombre,
                    onValueChange = { viewModel.onNombreChanged(it) },
                    label = "Nombre *",
                    icon = Icons.Default.Person,
                    error = viewModel.nombreError
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoTextoInput(
                    value = viewModel.email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = "Correo electrónico *",
                    icon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email,
                    error = viewModel.emailError
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoTextoInput(
                    value = viewModel.telefono,
                    onValueChange = { viewModel.onTelefonoChanged(it) },
                    label = "Teléfono (9 dígitos) *",
                    icon = Icons.Default.Phone,
                    keyboardType = KeyboardType.Number,
                    error = viewModel.telefonoError
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoDropdownInput(
                    value = viewModel.asunto,
                    onValueChange = { viewModel.onAsuntoChanged(it) },
                    label = "Asunto *",
                    opciones = viewModel.opcionesAsunto,
                    error = viewModel.asuntoError
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoTextoInput(
                    value = viewModel.mensaje,
                    onValueChange = { viewModel.onMensajeChanged(it) },
                    label = "Mensaje *",
                    singleLine = false,
                    minLines = 4,
                    maxLines = 6,
                    error = viewModel.mensajeError
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Pasamos `cargando = isLoading`
                BotonPrimario(
                    texto = "ENVIAR MENSAJE",
                    cargando = isLoading,
                    onClick = { viewModel.enviarFormulario {} }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        FooterInformativo()
    }
}