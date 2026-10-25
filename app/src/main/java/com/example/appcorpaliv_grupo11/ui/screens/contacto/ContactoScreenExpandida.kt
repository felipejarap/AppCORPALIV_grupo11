package com.example.appcorpaliv_grupo11.ui.screens.contacto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

@Composable
fun ContactoScreenExpandida(viewModel: ContactoViewModel) {
    val scrollState = rememberScrollState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isSubmitted by viewModel.isSubmitted.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // --- IZQUIERDA: TARJETA INSTITUCIONAL ---
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(32.dp)) {
                    Text(
                        text = "Centro de Atención Corpaliv",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Estamos a tu disposición para responder dudas sobre retiros, envíos y soporte general.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    ContactoItemExpandido(
                        icon = Icons.Default.LocationOn,
                        titulo = "General Salvo 20, Of. 110, Providencia.",
                        subtitulo = "Sede Institucional"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ContactoItemExpandido(
                        icon = Icons.Default.Email,
                        titulo = "mhasche@corpaliv.cl",
                        subtitulo = "Atención por Correo"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ContactoItemExpandido(
                        icon = Icons.Default.Phone,
                        titulo = "+56 9 9221 3113",
                        subtitulo = "WhatsApp / Llamadas"
                    )
                }
            }

            // --- DERECHA: FORMULARIO DE CONTACTO ---
            Column(modifier = Modifier.weight(1.4f)) {
                if (isSubmitted) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "¡Mensaje Enviado con Éxito!",
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Gracias por escribirnos. Nuestro equipo revisará tu solicitud a la brevedad.",
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(20.dp))
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
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Nombre *",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = viewModel.nombre,
                                onValueChange = { viewModel.onNombreChanged(it) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Correo electrónico *",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = viewModel.email,
                                onValueChange = { viewModel.onEmailChanged(it) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Asunto",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = viewModel.asunto,
                        onValueChange = { viewModel.onAsuntoChanged(it) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Mensaje *",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = viewModel.mensaje,
                        onValueChange = { viewModel.onMensajeChanged(it) },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 5
                    )

                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage!!,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { viewModel.enviarFormulario {} },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "ENVIAR MENSAJE",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        // --- FOOTER INFERIOR DE INFORMACIÓN ---
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoFooterItemExpandido(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.LocationOn,
                    titulo = "AGENDA TU RETIRO",
                    descripcion = "Para retirar tus compras en General Salvo 20, oficina 110, Providencia."
                )
                Spacer(modifier = Modifier.width(24.dp))
                InfoFooterItemExpandido(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.ShoppingCart,
                    titulo = "ENVIAMOS A TODO CHILE",
                    descripcion = "No importa dónde estés, podemos enviar tus productos donde elijas."
                )
                Spacer(modifier = Modifier.width(24.dp))
                InfoFooterItemExpandido(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Info,
                    titulo = "¿Y MI PEDIDO?",
                    descripcion = "Si tienes dudas con respecto a tu pedido, escríbenos al +569 9221 3413."
                )
            }
        }
    }
}

@Composable
private fun ContactoItemExpandido(
    icon: ImageVector,
    titulo: String,
    subtitulo: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(44.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitulo,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun InfoFooterItemExpandido(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    titulo: String,
    descripcion: String
) {
    Row(modifier = modifier, verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = descripcion,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}