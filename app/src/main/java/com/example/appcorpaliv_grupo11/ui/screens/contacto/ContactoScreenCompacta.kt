package com.example.appcorpaliv_grupo11.ui.screens.contacto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Subject
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.appcorpaliv_grupo11.ui.components.BotonPrimario
import com.example.appcorpaliv_grupo11.ui.components.CampoTextoInput
import com.example.appcorpaliv_grupo11.ui.components.FooterInformativo
import com.example.appcorpaliv_grupo11.ui.components.TarjetaExitoAnimada
import com.example.appcorpaliv_grupo11.ui.components.TarjetaInfoContacto
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

@Composable
fun ContactoScreenCompacta(viewModel: ContactoViewModel) {
    val scrollState = rememberScrollState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isSubmitted by viewModel.isSubmitted.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        // 1. Tarjeta superior de información de contacto
        TarjetaInfoContacto(
            modifier = Modifier.padding(16.dp)
        )

        // 2. Formulario / Tarjeta de éxito
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Componente animado de éxito
            TarjetaExitoAnimada(
                mostrarExito = isSubmitted,
                onEnviarOtroMensaje = { viewModel.reiniciarFormulario() }
            )

            // Campos del formulario (se ocultan mientras la tarjeta de éxito esté visible)
            if (!isSubmitted) {
                CampoTextoInput(
                    value = viewModel.nombre,
                    onValueChange = { viewModel.onNombreChanged(it) },
                    label = "Nombre *",
                    icon = Icons.Default.Person
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoTextoInput(
                    value = viewModel.email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = "Correo electrónico *",
                    icon = Icons.Default.Email
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoTextoInput(
                    value = viewModel.asunto,
                    onValueChange = { viewModel.onAsuntoChanged(it) },
                    label = "Asunto",
                    icon = Icons.AutoMirrored.Filled.Subject
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoTextoInput(
                    value = viewModel.mensaje,
                    onValueChange = { viewModel.onMensajeChanged(it) },
                    label = "Mensaje *",
                    singleLine = false,
                    minLines = 4,
                    maxLines = 6
                )

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                BotonPrimario(
                    texto = "ENVIAR MENSAJE",
                    onClick = { viewModel.enviarFormulario {} }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        FooterInformativo()
    }
}