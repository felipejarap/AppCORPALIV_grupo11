package com.example.appcorpaliv_grupo11.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * COMPONENTE REUTILIZABLE DE INSIGNIA DE ACREDITACIÓN (BADGE / CHIP INFORMATIVO)
 *
 * Componente visual desacoplado en forma de píldora que destaca la acreditación institucional
 * ante el Ministerio de Desarrollo Social (MIDESO) bajo el marco de la Ley 21.015 de inclusión laboral.
 *
 * @param texto Mensaje de la acreditación (por defecto "Acreditación MIDESO - Ley 21.015").
 * @param modifier Modificador para personalizar la maquetación externa desde el composable padre (primer parámetro opcional).
 */
@Composable
fun InsigniaMideso(
    modifier: Modifier = Modifier, // Reordenado: Primer parámetro opcional según las guías de Compose
    texto: String = "Acreditación MIDESO - Ley 21.015"
) {
    // Contenedor elíptico (píldora) con bordes redondeados al máximo (50 dp)
    Surface(
        shape = RoundedCornerShape(50.dp),
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono de verificación/aprobación oficial
            Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = null, // Ícono puramente decorativo
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            // Texto informativo de la norma o ley
            Text(
                text = texto,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}