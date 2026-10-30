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

@Composable
fun TarjetaExitoAnimada(
    mostrarExito: Boolean,
    onEnviarOtroMensaje: () -> Unit
) {
    // Auto-desaparición a los 5 segundos
    LaunchedEffect(mostrarExito) {
        if (mostrarExito) {
            delay(5.seconds) // Reemplaza a delay(5000) usando Duration
            onEnviarOtroMensaje()
        }
    }

    AnimatedVisibility(
        visible = mostrarExito,
        // Transición de Entrada: Desliza desde abajo + Fade + Leve aumento de tamaño (Pop)
        enter = fadeIn(animationSpec = tween(durationMillis = 400)) +
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight / 3 },
                    animationSpec = spring()
                ) +
                scaleIn(initialScale = 0.9f),
        // Transición de Salida: Desliza hacia arriba + Fade
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