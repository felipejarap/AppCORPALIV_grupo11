package com.example.appcorpaliv_grupo11.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * COMPONENTE REUTILIZABLE DE TARJETA DE PRODUCTO (PRODUCT CARD ITEM)
 *
 * Componente atómico de maquetación que representa un artículo individual de la tienda Alba Lab / Corpaliv.
 * Diseñado con un ancho fijo (170 dp) ideal para ser integrado dentro de listas de desplazamiento
 * horizontal (`LazyRow`) o cuadrículas (`LazyVerticalGrid`).
 *
 * @param nombre Denominación o título comercial del producto.
 * @param categoria Clasificación o categoría a la que pertenece el producto.
 * @param precio Cadena formateada del valor comercial (ej: "$12.990").
 * @param modifier Modificador para personalizar la maquetación externa desde el composable padre (primer parámetro opcional).
 */
@Composable
fun TarjetaProductoItem(
    nombre: String,
    categoria: String,
    precio: String,
    modifier: Modifier = Modifier // Reordenado: Primer parámetro opcional según las guías de Compose
) {
    // Tarjeta contenedora con ancho fijo accesible para carruseles horizontales
    Card(
        modifier = modifier.width(170.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Marco o contenedor de previsualización de imagen/ícono del producto
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = null, // Ícono decorativo de marcador de posición
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Categoría en mayúsculas sostenidas para resaltar la jerarquía tipográfica
            Text(
                text = categoria.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Nombre del producto restringido a 1 sola línea para mantener consistencia en la grid
            Text(
                text = nombre,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Precio destacado con grosor de fuente extra en negrita (ExtraBold)
            Text(
                text = precio,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}