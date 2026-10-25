package com.example.appcorpaliv_grupo11.ui.screens.contacto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Subject
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.appcorpaliv_grupo11.ui.components.BotonPrimario
import com.example.appcorpaliv_grupo11.ui.components.CampoTextoInput
import com.example.appcorpaliv_grupo11.ui.components.FooterInformativo
import com.example.appcorpaliv_grupo11.ui.components.TarjetaInfoContacto
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

@Composable
fun ContactoScreenMediana(viewModel: ContactoViewModel) {
    val scrollState = rememberScrollState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isSubmitted by viewModel.isSubmitted.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 640.dp)
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            TarjetaInfoContacto()

            Spacer(modifier = Modifier.height(20.dp))

            if (isSubmitted) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "¡Mensaje Enviado!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Gracias por contactarte con Corpaliv / Alba Lab. Te responderemos a la brevedad.",
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.reiniciarFormulario() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("Enviar otro mensaje")
                        }
                    }
                }
            } else {
                CampoTextoInput(
                    value = viewModel.nombre,
                    onValueChange = { viewModel.onNombreChanged(it) },
                    label = "Nombre *",
                    icon = Icons.Default.Person
                )

                Spacer(modifier = Modifier.height(14.dp))

                CampoTextoInput(
                    value = viewModel.email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = "Correo electrónico *",
                    icon = Icons.Default.Email
                )

                Spacer(modifier = Modifier.height(14.dp))

                CampoTextoInput(
                    value = viewModel.asunto,
                    onValueChange = { viewModel.onAsuntoChanged(it) },
                    label = "Asunto",
                    icon = Icons.AutoMirrored.Filled.Subject
                )

                Spacer(modifier = Modifier.height(14.dp))

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
                        fontSize = 13.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                BotonPrimario(
                    texto = "ENVIAR MENSAJE",
                    onClick = { viewModel.enviarFormulario {} }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f, fill = false))
        Spacer(modifier = Modifier.height(24.dp))

        FooterInformativo()
    }
}