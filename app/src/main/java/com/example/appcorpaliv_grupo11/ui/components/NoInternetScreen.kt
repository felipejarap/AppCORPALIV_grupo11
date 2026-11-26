package com.example.appcorpaliv_grupo11.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
    modifier: Modifier = Modifier
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
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Sin conexión",
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¡Sin Conexión a Internet!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Esta aplicación requiere una conexión activa a internet para funcionar. Por favor, conéctate a una red Wi-Fi o datos móviles.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

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
 * BANNER COMPACTO DE ESTADO DE RED / SERVICIO NO DISPONIBLE
 *
 * Muestra un aviso visual seguro dentro de pantallas con contenido mixto
 * (ej. ContactoScreen o ListaContactosScreen) cuando el backend no responde,
 * permitiendo reintentar la operación sin perder los datos ingresados.
 */
@Composable
fun BannerEstadoRed(
    mensaje: String = "Servicio temporalmente no disponible. Verifica tu conexión e intenta nuevamente.",
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Aviso de conexión",
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
            Text(
                text = mensaje,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = onRetry) {
                Text(
                    text = "Reintentar",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

/**
 * PREVISUALIZACIÓN DE LA PANTALLA SIN CONEXIÓN
 */
@Preview(showBackground = true)
@Composable
fun NoInternetScreenPreview() {
    MaterialTheme {
        NoInternetScreen(onRetry = {})
    }
}