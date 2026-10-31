package com.example.appcorpaliv_grupo11.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * PANTALLA DE BLOQUEO POR FALTA DE CONEXIÓN A INTERNET
 *
 * Vista de contingencia que se despliega cuando el dispositivo pierde la conectividad a la red.
 * Notifica al usuario de forma clara sobre la interrupción del servicio y le proporciona
 * un mecanismo explícito para reintentar la comprobación de estado.
 *
 * @param onRetry Callback que ejecuta la verificación de estado de red al presionar el botón "Reintentar Conexión".
 * @param modifier Modificador para personalizar la maquetación externa desde el composable padre (primer parámetro opcional).
 */
@Composable
fun NoInternetScreen(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier // Reordenado: Primer parámetro opcional según las guías de Compose
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ícono de advertencia destacado de gran tamaño (80 dp) en color de error
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Sin conexión",
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título principal de aviso de estado
            Text(
                text = "¡Sin Conexión a Internet!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mensaje explicativo sobre la necesidad de datos móviles o wifi
            Text(
                text = "Esta aplicación requiere una conexión activa a internet para funcionar. Por favor, conéctate a una red Wi-Fi o datos móviles.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de reintento de conexión ajustado al 70% del ancho
            Button(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Reintentar Conexión")
            }
        }
    }
}

/**
 * PREVISUALIZACIÓN DE LA PANTALLA SIN CONEXIÓN
 * Permite visualizar el diseño de la interfaz directamente en el canvas de Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun NoInternetScreenPreview() {
    MaterialTheme {
        NoInternetScreen(onRetry = {})
    }
}