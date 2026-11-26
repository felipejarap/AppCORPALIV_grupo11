package com.example.appcorpaliv_grupo11.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

/**
 * COMPONENTE REUTILIZABLE DE NOTIFICACIÓN ANIMADA DE ÉXITO (ANIMATED SUCCESS CARD)
 *
 * Componente visual dinámico que retroalimenta al usuario tras el envío exitoso de un formulario.
 * Incorpora animaciones de entrada y salida fluidas mediante [AnimatedVisibility] y gestiona un
 * temporizador de auto-desaparición asíncrono a través de [LaunchedEffect].
 *
 * @param mostrarExito Estado booleano que controla la visibilidad y ejecución de las animaciones.
 * @param onEnviarOtroMensaje Callback para reiniciar el formulario o cerrar la tarjeta de confirmación.
 * @param modifier Modificador para personalizar la maquetación externa desde el composable padre (primer parámetro opcional).
 */
@Composable
fun TarjetaExitoAnimada(
    mostrarExito: Boolean,
    onEnviarOtroMensaje: () -> Unit,
    modifier: Modifier = Modifier // Reordenado: Primer parámetro opcional según las guías de Compose
) {
    // Efeclo secundario que temporiza la auto-desaparición a los 5 segundos
    LaunchedEffect(mostrarExito) {
        if (mostrarExito) {
            delay(5.seconds) // Pausa la corrutina durante 5 segundos de forma limpia usando Duration API
            onEnviarOtroMensaje()
        }
    }

    // Envoltorio de animación declarativa para la entrada y salida de la tarjeta
    AnimatedVisibility(
        visible = mostrarExito,
        modifier = modifier,
        // Transición de Entrada: Desliza desde abajo (Spring) + Fade + Leve aumento de escala
        enter = fadeIn(animationSpec = tween(durationMillis = 400)) +
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight / 3 },
                    animationSpec = spring()
                ) +
                scaleIn(initialScale = 0.9f),
        // Transición de Salida: Desliza hacia arriba + Fade + Contracción de escala
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) +
                slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight / 3 },
                    animationSpec = tween(durationMillis = 300)
                ) +
                scaleOut(targetScale = 0.95f)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¡Mensaje Enviado!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Gracias por contactarte con Corpaliv / Alba Lab.\nTe responderemos a la brevedad.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onEnviarOtroMensaje,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Enviar otro mensaje")
                }
            }
        }
    }
}