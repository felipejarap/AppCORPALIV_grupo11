package com.example.appcorpaliv_grupo11.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * COMPONENTE REUTILIZABLE DE PIE DE PÁGINA INFORMATIVO (FOOTER)
 *
 * Componente visual estático de maquetación que despliega información clave sobre la institución
 * o punto de venta (Dirección física para retiro presencial y cobertura de envíos).
 * Utiliza una distribución equitativa basada en pesos (`Modifier.weight(1f)`) para adaptarse de forma
 * proporcional a cualquier ancho de pantalla en smartphones o tablets.
 *
 * @param modifier Modificador para personalizar la maquetación externa desde la vista contenedora.
 */
@Composable
fun FooterInformativo(
    modifier: Modifier = Modifier
) {
    // Tarjeta contenedora elevada con bordes redondeados y opacidad aplicada a la variante de superficie
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // =========================================================================
            // BLOQUE 1: DIRECCIÓN DE RETIRO PRESENCIAL (OCUPA EL 50% DEL ANCHO)
            // =========================================================================
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null, // Ícono puramente decorativo
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "AGENDA TU RETIRO",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "General Salvo 20, Of. 110, Providencia",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // =========================================================================
            // BLOQUE 2: COBERTURA DE ENVÍOS (OCUPA EL 50% DEL ANCHO)
            // =========================================================================
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null, // Ícono puramente decorativo
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "ENVIAMOS A TODO CHILE",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Despachos directos a todo el país",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}