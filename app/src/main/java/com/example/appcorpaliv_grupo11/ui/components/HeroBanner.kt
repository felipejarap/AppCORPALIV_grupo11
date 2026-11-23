package com.example.appcorpaliv_grupo11.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * COMPONENTE REUTILIZABLE DE BÁNER PRINCIPAL DE BIENVENIDA (HERO BANNER)
 *
 * Componente visual destacado ubicado en la parte superior de la pantalla principal.
 * Presenta el propósito institucional de la organización ("Alba Lab de Corpaliv")
 * y expone dos llamadas a la acción (*Call to Action - CTA*) principales para la navegación.
 *
 * @param onVerTiendaClick Evento lambda que se gatilla al presionar el botón "Ver Tienda".
 * @param onContactoClick Evento lambda que se gatilla al presionar el botón "Contacto".
 * @param modifier Modificador para personalizar la maquetación externa desde el composable padre (primer parámetro opcional).
 */
@Composable
fun HeroBanner(
    onVerTiendaClick: () -> Unit,
    onContactoClick: () -> Unit,
    modifier: Modifier = Modifier // Ordenado como primer parámetro opcional según las guías de Compose
) {
    // Tarjeta contenedora principal con esquinas suavemente redondeadas de 24 dp
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Insignia o chip superior que identifica la marca institucional
            Surface(
                shape = RoundedCornerShape(50.dp),
                color = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text(
                    text = "Somos Alba Lab de Corpaliv",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            // Título o eslogan principal con tipografía semántica titleLarge
            Text(
                text = "Inclusión sociolaboral de jóvenes y adultos con discapacidad múltiple",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Fila de botones de acción rápida para la navegación interna
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Acción primaria: Botón con fondo sólido para destacar la tienda
                Button(
                    onClick = onVerTiendaClick,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = null, // Ícono decorativo
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ver Tienda", fontWeight = FontWeight.Bold)
                }

                // Acción secundaria: Botón delineado para dirigir al formulario de contacto
                OutlinedButton(
                    onClick = onContactoClick,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Contacto", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}