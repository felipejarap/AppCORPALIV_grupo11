package com.example.appcorpaliv_grupo11.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcorpaliv_grupo11.viewModel.AuthViewModel

/**
 * PANTALLA DE LOGIN EN FORMATO EXPANDIDO (TABLETS EN LANDSCAPE / PANTALLAS ANCHAS)
 *
 * Sub-vista adaptativa diseñada para pantallas de más de 840 dp de ancho.
 * Aprovecha el espacio horizontal estructurando el contenido en dos columnas paralelas
 * mediante un [Row] de proporciones ponderadas (`weight`):
 * 1. Columna Izquierda (45% - weight 1f): Banner de marca institucional.
 * 2. Columna Derecha (55% - weight 1.2f): Formulario interactivo enlazado al [AuthViewModel].
 *
 * @param authViewModel Instancia del ViewModel que gestiona la lógica de autenticación y estados de entrada.
 * @param onLoginSuccess Callback a ejecutar cuando las credenciales son validadas con éxito.
 */
@Composable
fun LoginScreenExpandida(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit = {}
) {
    // Suscripción al estado de error de inicio de sesión desde el StateFlow del ViewModel
    val loginError by authViewModel.loginError.collectAsState()

    // Estado mutable de desplazamiento para prevenir desbordamientos
    val scrollState = rememberScrollState()

    // Contenedor principal con margen amplio (48 dp) y alineación centrada
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(48.dp),
        contentAlignment = Alignment.Center
    ) {
        // Tarjeta contenedora de dos columnas con ancho máximo de 900 dp
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 900.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            // Contenedor horizontal ajustado a la altura intrínseca mínima de sus componentes
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                // =========================================================================
                // COLUMNA 1: BANNER INSTITUCIONAL DE BIENVENIDA (IZQUIERDA)
                // =========================================================================
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null, // Ícono decorativo
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Bienvenido al Panel de Administración",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Gestiona solicitudes, mensajes de usuarios y estados del sistema Corpaliv / Alba Lab de manera centralizada y segura.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                    }
                }

                // =========================================================================
                // COLUMNA 2: FORMULARIO DE INGRESO DE CREDENCIALES (DERECHA)
                // =========================================================================
                Column(
                    modifier = Modifier
                        .weight(1.2f)
                        .padding(40.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Acceso Funcionarios",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Ingresa tus credenciales institucionales para continuar.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Campo 1: Usuario o correo institucional
                    OutlinedTextField(
                        value = authViewModel.usuarioInput,
                        onValueChange = { authViewModel.onUsuarioChanged(it) },
                        label = { Text("Usuario o correo institucional") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo 2: Contraseña con máscara de seguridad
                    OutlinedTextField(
                        value = authViewModel.passwordInput,
                        onValueChange = { authViewModel.onPasswordChanged(it) },
                        label = { Text("Contraseña") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Renderizado condicional del mensaje de error
                    if (loginError != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = loginError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Botón de activación de inicio de sesión
                    Button(
                        onClick = { authViewModel.iniciarSesion(onSuccess = onLoginSuccess) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = "INGRESAR AL PANEL",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}