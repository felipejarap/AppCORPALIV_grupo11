package com.example.appcorpaliv_grupo11.ui.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Subject
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.appcorpaliv_grupo11.model.ContactoEntity

/**
 * COMPONENTE REUTILIZABLE DE ELEMENTO DE TARJETA DE MENSAJE / CONTACTO
 *
 * Tarjeta interactiva que despliega los detalles de una consulta o mensaje enviado por un usuario.
 * Integra el acceso directo a recursos nativos del sistema mediante Intents Implícitos
 * (Cliente de Correo y Marcador Telefónico) y expone acciones administrativas para modificar
 * el estado del registro ("Pendiente", "Contestado", "Rechazado") en la base de datos Room.
 *
 * Cumplimiento Criterio IE 2.4.1 (Integración con Recursos del Dispositivo):
 * - Acción de Correo: Lanza `Intent.ACTION_SENDTO` abriendo la app de correo del sistema (Gmail, etc.).
 * - Acción de Teléfono: Lanza `Intent.ACTION_DIAL` abriendo el marcador nativo con formato estándar (+56).
 *
 * @param contacto Objeto de entidad [ContactoEntity] con la información almacenada en Room.
 * @param onCambiarEstado Callback que notifica al ViewModel la actualización de estado del registro.
 * @param modifier Modificador para personalizar la maquetación externa desde el composable padre (primer parámetro opcional).
 */
@Composable
fun TarjetaMensajeItem(
    contacto: ContactoEntity,
    onCambiarEstado: (ContactoEntity, String) -> Unit,
    modifier: Modifier = Modifier // Reordenado: Primer parámetro opcional según las guías de Compose
) {
    // Obtención del contexto nativo de Android para lanzar las intenciones de sistema (Intents)
    val context = LocalContext.current

    // Paleta de colores dinámica asignada en función del estado del registro en la base de datos
    val (colorContenedor, colorTexto) = when (contacto.estado) {
        "Contestado" -> Pair(Color(0xFFE8F5E9), Color(0xFF2E7D32)) // Verde Claro / Oscuro
        "Rechazado" -> Pair(Color(0xFFFFEBEE), Color(0xFFC62828))  // Rojo Claro / Oscuro
        else -> Pair(Color(0xFFFFF3E0), Color(0xFFE65100))         // Naranja Claro / Oscuro (Pendiente)
    }

    // Extracción y formateo preventivo del número de teléfono
    val telefonoMostrar = when {
        contacto.telefono.isNotBlank() -> contacto.telefono
        contacto.asunto.contains("Tel:") -> contacto.asunto.substringAfter("Tel:").trim()
        else -> "Sin teléfono"
    }

    // Saneamiento de la cadena del asunto si contenía datos concatenados
    val asuntoLimpio = if (contacto.asunto.contains("| Tel:")) {
        contacto.asunto.substringBefore("| Tel:").trim()
    } else {
        contacto.asunto
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // =========================================================================
            // 1. CABECERA: NOMBRE DEL EMISOR Y BADGE DE ESTADO
            // =========================================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null, // Ícono decorativo
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = contacto.nombre.ifBlank { "Sin nombre" },
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Badge o etiqueta de estado con color dinámico según la categoría
                Surface(
                    color = colorContenedor,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = contacto.estado,
                        color = colorTexto,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            // =========================================================================
            // 2. RECURSOS NATIVOS: CORREO Y TELÉFONO INTERACTIVOS (INTENTS IMPLÍCITOS KTX)
            // =========================================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // ---------------------------------------------------------------------
                // RECURSO NATIVO 1: Enviar Correo (ACTION_SENDTO + KTX toUri)
                // ---------------------------------------------------------------------
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .clickable {
                            if (contacto.email.isNotBlank()) {
                                val intentEmail = Intent(Intent.ACTION_SENDTO).apply {
                                    data = "mailto:${contacto.email}".toUri()
                                    putExtra(Intent.EXTRA_SUBJECT, "Respuesta a: $asuntoLimpio")
                                }
                                context.startActivity(intentEmail)
                            }
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Enviar Correo",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = contacto.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // ---------------------------------------------------------------------
                // RECURSO NATIVO 2: Marcador Telefónico con Prefijo +56 (ACTION_DIAL + KTX toUri)
                // ---------------------------------------------------------------------
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        val digitsOnly = telefonoMostrar.filter { it.isDigit() }
                        if (digitsOnly.isNotBlank()) {
                            // Antepone el código de país +56 si el número es de 9 dígitos (Chile)
                            val numFinal = if (digitsOnly.length == 9) {
                                "+56$digitsOnly"
                            } else {
                                "+$digitsOnly"
                            }

                            val intentLlamar = Intent(Intent.ACTION_DIAL).apply {
                                data = "tel:$numFinal".toUri()
                            }
                            context.startActivity(intentLlamar)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Llamar por teléfono",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = telefonoMostrar,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // =========================================================================
            // 3. ASUNTO Y DETALLE DEL MENSAJE
            // =========================================================================
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Subject,
                    contentDescription = null, // Ícono decorativo
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = asuntoLimpio,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = contacto.mensaje,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )

            // =========================================================================
            // 4. ACCIONES DE ADMINISTRACIÓN (Visibles únicamente para estado "Pendiente")
            // =========================================================================
            if (contacto.estado.equals("Pendiente", ignoreCase = true)) {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { onCambiarEstado(contacto, "Contestado") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Atender")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = { onCambiarEstado(contacto, "Rechazado") },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Rechazar")
                    }
                }
            }
        }
    }
}