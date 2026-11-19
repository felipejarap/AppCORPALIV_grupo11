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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcorpaliv_grupo11.viewModel.AuthViewModel

/**
 * PANTALLA DE LOGIN EN FORMATO COMPACTO (SMARTPHONES PORTRAIT)
 *
 * Sub-vista adaptativa para teléfonos en orientación vertical. Integra interacción
 * directa con [AuthViewModel] mediante el consumo de estados reactivos (`collectAsState`),
 * ocultamiento seguro de contraseña ([PasswordVisualTransformation]) y desplazamiento vertical
 * ([rememberScrollState]) para prevenir solapamientos cuando el teclado virtual está visible.
 *
 * @param authViewModel Instancia del ViewModel que gestiona la lógica de autenticación y estados de entrada.
 * @param onLoginSuccess Callback a ejecutar cuando las credenciales son validadas con éxito.
 */
@Composable
fun LoginScreenCompacta(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit = {}
) {
    // Suscripción al estado de error de inicio de sesión desde el StateFlow del ViewModel
    val loginError by authViewModel.loginError.collectAsState()

    // Estado mutable de desplazamiento para permitir scroll cuando se despliega el teclado suave
    val scrollState = rememberScrollState()

    // Contenedor principal centrado que ocupa todo el ancho y alto disponible
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        // Tarjeta contenedora de formulario restringida a un ancho máximo de 420 dp
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 420.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título de acceso administrativo
                Text(
                    text = "Acceso Funcionarios",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Descripción de propósito del módulo
                Text(
                    text = "Inicia sesión para gestionar los mensajes y solicitudes de contacto.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo 1: Nombre de usuario enlazado directamente al ViewModel
                OutlinedTextField(
                    value = authViewModel.usuarioInput,
                    onValueChange = { authViewModel.onUsuarioChanged(it) },
                    label = { Text("Usuario") },
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

                Spacer(modifier = Modifier.height(14.dp))

                // Campo 2: Contraseña con máscara de seguridad y tipo de teclado numérico/alfanumérico
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

                // Renderizado condicional del mensaje de error de autenticación
                if (loginError != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = loginError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de activación de intento de inicio de sesión
                Button(
                    onClick = { authViewModel.iniciarSesion(onSuccess = onLoginSuccess) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = "INGRESAR",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}