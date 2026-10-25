package com.example.appcorpaliv_grupo11.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.appcorpaliv_grupo11.ui.components.FooterInformativo
import com.example.appcorpaliv_grupo11.ui.components.HeroBanner
import com.example.appcorpaliv_grupo11.ui.components.InsigniaMideso
import com.example.appcorpaliv_grupo11.ui.components.TarjetaProductoItem
import com.example.appcorpaliv_grupo11.viewModel.MainViewModel

@Composable
fun HomeScreenCompacta(
    navController: NavController,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // 1. Insignia MIDESO
        InsigniaMideso(
            texto = "Acreditación MIDESO - Ley 21.015"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Hero Banner principal con botones
        HeroBanner(
            onVerTiendaClick = {
                // Navegación al catálogo o acción requerida
            },
            onContactoClick = {
                navController.navigate("contacto") // Ajusta según tu objeto de rutas (ej: Screen.Contacto.route)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Título de la sección de productos
        Text(
            text = "Nuestros Productos Destacados",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 4. Carrusel Horizontal de Productos
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(5) { index ->
                TarjetaProductoItem(
                    nombre = "Producto Eco #${index + 1}",
                    categoria = "Inclusión",
                    precio = "$${(index + 1) * 2500}"
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 5. Footer Informativo
        FooterInformativo()
    }
}