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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

@Composable
fun ContactoScreenMediana(viewModel: ContactoViewModel) {
    val scrollState = rememberScrollState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isSubmitted by viewModel.isSubmitted.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        // --- 1. HEADER SUPERIOR INSTITUCIONAL ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
                )
                .padding(horizontal = 28.dp, vertical = 28.dp)
        ) {
            Text(
                text = "Contacto",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E1E)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.35f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "General Salvo 20, Of. 10, Providencia.",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color(0xFF1E1E1E)
                    )
                    Text(text = "Ubicación institucional", fontSize = 11.sp, color = Color(0xFF4A4A4A))
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.35f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "mhasche@corpaliv.cl",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color(0xFF1E1E1E)
                    )
                    Text(text = "Correo electrónico", fontSize = 11.sp, color = Color(0xFF4A4A4A))
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.35f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "+56 9 9221 3113",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color(0xFF1E1E1E)
                    )
                    Text(text = "Teléfono de contacto", fontSize = 11.sp, color = Color(0xFF4A4A4A))
                }
            }
        }

        // --- 2. CONTENIDO EN DOS COLUMNAS ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Box(modifier = Modifier.weight(1.2f)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (isSubmitted) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "¡Mensaje Enviado!", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.secondary)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Gracias por contactarte con Corpaliv / Alba Lab. Te responderemos a la brevedad.", fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { viewModel.reiniciarFormulario() },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                                ) {
                                    Text("Nuevo mensaje")
                                }
                            }
                        }
                    } else {
                        Text("Nombre *", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = viewModel.nombre,
                            onValueChange = { viewModel.onNombreChanged(it) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Correo electrónico *", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = viewModel.email,
                            onValueChange = { viewModel.onEmailChanged(it) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Asunto", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = viewModel.asunto,
                            onValueChange = { viewModel.onAsuntoChanged(it) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Mensaje *", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = viewModel.mensaje,
                            onValueChange = { viewModel.onMensajeChanged(it) },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 4
                        )

                        if (errorMessage != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = { viewModel.enviarFormulario {} },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                            modifier = Modifier
                                .width(140.dp)
                                .height(48.dp)
                        ) {
                            Text("ENVIAR", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(0.8f)
                    .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("AGENDA TU RETIRO", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("Para retirar tus compras en General Salvo 20, oficina 10, Providencia.", fontSize = 12.sp, color = Color.Gray)
                        }
                    }

                    Row(verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("ENVIAMOS A TODO CHILE", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("No importa dónde estés, podemos enviar tus productos donde elijas.", fontSize = 12.sp, color = Color.Gray)
                        }
                    }

                    Row(verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("¿Y MI PEDIDO?", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("Si tienes dudas con respecto a tu pedido, escríbenos al +569 9221 3113.", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}