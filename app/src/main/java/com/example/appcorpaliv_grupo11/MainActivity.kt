package com.example.appcorpaliv_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appcorpaliv_grupo11.navigation.AppNavigation
import com.example.appcorpaliv_grupo11.ui.theme.AppCORPALIV_grupo11Theme
import com.example.appcorpaliv_grupo11.ui.utils.isInternetAvailable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // rememberSaveable mantiene el estado de isDarkTheme activo incluso tras rotar la pantalla
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }

            AppCORPALIV_grupo11Theme(darkTheme = isDarkTheme) {
                val context = LocalContext.current

                // Estado que almacena si hay conexión a internet
                var hasInternet by remember { mutableStateOf(isInternetAvailable(context)) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (hasInternet) {
                        // Si hay internet, cargamos la navegación pasando el control de tema
                        AppNavigation(
                            isDarkTheme = isDarkTheme,
                            onToggleTheme = { isDarkTheme = !isDarkTheme }
                        )
                    } else {
                        // Si NO hay internet, mostramos la pantalla de bloqueo
                        NoInternetScreen(
                            onRetry = {
                                hasInternet = isInternetAvailable(context)
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Pantalla de bloqueo cuando el dispositivo no tiene conexión a internet.
 */
@Composable
fun NoInternetScreen(onRetry: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
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