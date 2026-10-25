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
fun HomeScreenMediana(
    navController: NavController,
    viewModel: MainViewModel
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // --- 1. BANNER DUAL ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Card(
                modifier = Modifier.weight(1.3f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Somos Alba Lab de Corpaliv", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Inclusión sociolaboral de jóvenes y adultos con discapacidad múltiple", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController.navigate(Screen.Contacto.route) },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Ver Tienda y Contacto")
                    }
                }
            }

            Card(
                modifier = Modifier.weight(0.9f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.Center) {
                    Icon(Icons.Default.Favorite, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Servicios e Inclusión", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Capacitaciones y acompañamiento continuo para empresas e instituciones.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        // --- 2. SECCIÓN DE PRODUCTOS DESTACADOS ---
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Nuestra Tienda",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Productos fabricados por jóvenes y adultos con diversidad funcional",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(listaProductosEjemplo) { producto ->
                    Card(
                        modifier = Modifier.width(170.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(producto.categoria.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(producto.nombre, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1, color = MaterialTheme.colorScheme.onSurface)
                            Text(producto.precio, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        // --- 3. PROGRAMAS ---
        Text("Nuestros Programas", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Groups, contentDescription = null, modifier = Modifier.size(36.dp), tint = MaterialTheme.colorScheme.onSecondaryContainer)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Taller Sociolaboral", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        Text("Formación en habilidades para la autonomía.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f))
                    }
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.BusinessCenter, contentDescription = null, modifier = Modifier.size(36.dp), tint = MaterialTheme.colorScheme.onTertiaryContainer)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Intermediación Laboral", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiaryContainer)
                        Text("Acompañamiento directo en puestos de trabajo.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f))
                    }
                }
            }
        }

        // --- 4. FOOTER ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Retiro en General Salvo 20, Of. 110, Providencia.", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Envíos a todo Chile", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}