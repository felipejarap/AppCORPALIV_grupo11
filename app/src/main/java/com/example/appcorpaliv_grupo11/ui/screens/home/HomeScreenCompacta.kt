package com.example.appcorpaliv_grupo11.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appcorpaliv_grupo11.navigation.Screen
import com.example.appcorpaliv_grupo11.viewModel.MainViewModel

data class ProductoHome(val nombre: String, val precio: String, val categoria: String)

val listaProductosEjemplo = listOf(
    ProductoHome("Bolsa Alba Lab", "$4.000", "Otros"),
    ProductoHome("Difusor Colgante", "$7.500", "Bienestar"),
    ProductoHome("Difusor Aroma 100ml", "$7.500", "Bienestar"),
    ProductoHome("Farol de Luz", "$11.900", "Hogar"),
    ProductoHome("Guatero de Semillas", "$11.990", "Bienestar")
)

@Composable
fun HomeScreenCompacta(
    navController: NavController,
    viewModel: MainViewModel
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp)
    ) {
        // Hero Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
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

                Text(
                    text = "Inclusión sociolaboral de jóvenes y adultos con discapacidad múltiple",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = { navController.navigate(Screen.Contacto.route) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ver Tienda", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { navController.navigate(Screen.Contacto.route) },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Contacto", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tienda Carrusel
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Nuestra Tienda",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listaProductosEjemplo) { producto ->
                    Card(
                        modifier = Modifier.width(150.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(90.dp),
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(producto.categoria.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(producto.nombre, fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1, color = MaterialTheme.colorScheme.onSurface)
                            Text(producto.precio, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Programas
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text("Nuestros Programas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Icon(Icons.Default.Groups, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Taller Sociolaboral", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        Text("Formación en autonomía e inclusión.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f))
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Icon(Icons.Default.BusinessCenter, contentDescription = null, tint = MaterialTheme.colorScheme.onTertiaryContainer)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Intermediación Laboral", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onTertiaryContainer)
                        Text("Acompañamiento en empresas.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Footer
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ItemInfoHome(Icons.Default.LocationOn, "AGENDA TU RETIRO", "Providencia, General Salvo 20, Of. 110")
                ItemInfoHome(Icons.Default.ShoppingCart, "ENVIAMOS A TODO CHILE", "Despachos coordinados a todo el país")
            }
        }
    }
}

@Composable
private fun ItemInfoHome(icon: ImageVector, titulo: String, desc: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(titulo, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(desc, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}