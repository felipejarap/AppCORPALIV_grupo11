package com.example.appcorpaliv_grupo11.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * COMPONENTE REUTILIZABLE DE TARJETA DE INFORMACIÓN DE CONTACTO
 *
 * Componente atómico de maquetación que agrupa los canales oficiales de comunicación
 * e interacción institucionales de Corpaliv / Alba Lab (Dirección física, correo, teléfono y redes sociales).
 * Descompone la interfaz en sub-composables privados ([ItemInfoContactoDetalle] y [BotonRedSocial])
 * para maximizar la legibilidad y modularidad interna.
 *
 * @param modifier Modificador para personalizar la maquetación externa desde la vista contenedora.
 */
@Composable
fun TarjetaInfoContacto(
    modifier: Modifier = Modifier
) {
    // Tarjeta contenedora con esquinas redondeadas de 24 dp y fondo semántico primaryContainer
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Encabezado principal de la tarjeta
            Text(
                text = "Contacto",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Canal 1: Dirección física de la sede
            ItemInfoContactoDetalle(
                icono = Icons.Default.LocationOn,
                titulo = "General Salvo 20, Of. 110, Providencia.",
                subtitulo = "Ubicación"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Canal 2: Correo electrónico corporativo
            ItemInfoContactoDetalle(
                icono = Icons.Default.Email,
                titulo = "mhasche@corpaliv.cl",
                subtitulo = "Email"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Canal 3: Teléfono de contacto
            ItemInfoContactoDetalle(
                icono = Icons.Default.Phone,
                titulo = "+569 9221 3413",
                subtitulo = "Teléfono"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Fila de botones circulares para accesos a Redes Sociales
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BotonRedSocial(texto = "ig") // Instagram
                BotonRedSocial(texto = "in") // LinkedIn
            }
        }
    }
}

/**
 * SUB-COMPONENTE PRIVADO PARA RENDERIZAR CADA LÍNEA DETALLADA DE CONTACTO
 *
 * @param icono Vector gráfico representativo del canal de contacto.
 * @param titulo Texto principal del dato de contacto (ej: dirección o número).
 * @param subtitulo Etiqueta secundaria explicativa (ej: "Email" o "Ubicación").
 */
@Composable
private fun ItemInfoContactoDetalle(
    icono: ImageVector,
    titulo: String,
    subtitulo: String
) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = icono,
            contentDescription = null, // Ícono puramente decorativo
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(22.dp)
                .padding(top = 2.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = titulo,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = subtitulo,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}

/**
 * SUB-COMPONENTE PRIVADO DE BOTÓN CIRCULAR PARA REDES SOCIALES
 *
 * @param texto Sigla o inicial de la red social a mostrar dentro del ícono circular.
 */
@Composable
private fun BotonRedSocial(texto: String) {
    Surface(
        modifier = Modifier.size(36.dp),
        shape = CircleShape, // Ocupa un molde elíptico/circular perfecto
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = texto,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}