package com.example.appcorpaliv_grupo11.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appcorpaliv_grupo11.navigation.Screen
import com.example.appcorpaliv_grupo11.viewModel.MainViewModel

@Composable
fun HomeScreenExpandida(
    navController: NavController,
    viewModel: MainViewModel
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(36.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // --- 1. BANNER PRINCIPAL ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(36.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1.5f)) {
                    Text("Somos Alba Lab de Corpaliv", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Inclusión sociolaboral de jóvenes y adultos con discapacidad múltiple", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Button(
                            onClick = { navController.navigate(Screen.Contacto.route) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.height(48.dp)
                        ) {
                            Icon(Icons.Default.ShoppingBag, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Explorar Productos", fontWeight = FontWeight.Bold)
                        }
                        OutlinedButton(
                            onClick = { navController.navigate(Screen.Contacto.route) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.height(48.dp)
                        ) {
                            Text("Contactar Equipo", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // --- 2. SECCIÓN DE PRODUCTOS DESTACADOS ---
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Nuestra Tienda",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Revisa nuestro catálogo online con productos fabricados por jóvenes y adultos con diversidad funcional",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(listaProductosEjemplo) { producto ->
                    Card(
                        modifier = Modifier.width(200.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                shape = RoundedCornerShape(14.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(producto.categoria.uppercase(), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(producto.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1, color = MaterialTheme.colorScheme.onSurface)
                            Text(producto.precio, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        // --- 3. PROGRAMAS Y SERVICIOS ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(28.dp)) {
                    Icon(Icons.Default.Groups, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Taller Sociolaboral", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Ofrecemos programas para fomentar la exploración y el asesoramiento para promover la inclusión sociolaboral.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(28.dp)) {
                    Icon(Icons.Default.BusinessCenter, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Intermediación Laboral", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Acompañamos el proceso de inserción y adaptación laboral en empresas con seguimiento personalizado.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(28.dp)) {
                    Icon(Icons.Default.Favorite, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Servicios a Empresas", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Análisis de puesto de trabajo, capacitaciones en inclusión y evaluación de accesibilidad universal.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        // --- 4. FOOTER ---
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ) {
            Row(
                modifier = Modifier.padding(28.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("AGENDA TU RETIRO", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("General Salvo 20, Of. 110, Providencia", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("ENVIAMOS A TODO CHILE", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Despachos directos a todo el territorio nacional", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}