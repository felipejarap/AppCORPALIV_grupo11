package com.example.appcorpaliv_grupo11.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appcorpaliv_grupo11.model.ContactoEntity

/**
 * Componente visual de la tarjeta de mensaje.
 *
 * Muestra las acciones de "Rechazar" y "Contestar" **únicamente** cuando el estado del mensaje es "Pendiente".
 *
 * @param contacto Instancia del modelo [ContactoEntity] con la información del remitente.
 * @param onRechazarClick Callback ejecutado al presionar el botón "Rechazar".
 * @param onContestarClick Callback ejecutado al presionar el botón "Contestar".
 * @param modifier Modificador de diseño opcional.
 */
@Composable
fun TarjetaMensajeItem(
    contacto: ContactoEntity,
    onRechazarClick: () -> Unit,
    onContestarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val esPendiente = contacto.estado.equals("Pendiente", ignoreCase = true)

    val (colorFondoBadge, colorTextoBadge) = when (contacto.estado.lowercase()) {
        "contestado" -> Pair(Color(0xFFE8DEF8), Color(0xFF65558F))
        "rechazado" -> Pair(MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.onErrorContainer)
        else -> Pair(Color(0xFFFFE0B2), Color(0xFFE65100))
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = contacto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = colorFondoBadge
                ) {
                    Text(
                        text = contacto.estado.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorTextoBadge,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = contacto.email,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            if (!contacto.asunto.isNullOrBlank()) {
                Text(
                    text = "Asunto: ${contacto.asunto}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = contacto.mensaje,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Se muestran las acciones de gestión únicamente si el mensaje sigue "Pendiente"
            if (esPendiente) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onRechazarClick,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Rechazar",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Rechazar")
                    }

                    Button(
                        onClick = onContestarClick,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Contestar",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Contestar")
                    }
                }
            }
        }
    }
}