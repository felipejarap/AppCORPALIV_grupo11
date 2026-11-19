package com.example.appcorpaliv_grupo11.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.appcorpaliv_grupo11.ui.components.FooterInformativo
import com.example.appcorpaliv_grupo11.ui.components.HeroBanner
import com.example.appcorpaliv_grupo11.ui.components.InsigniaMideso
import com.example.appcorpaliv_grupo11.ui.components.TarjetaProductoItem
import com.example.appcorpaliv_grupo11.viewModel.MainViewModel

/**
 * PANTALLA PRINCIPAL DE INICIO EN FORMATO MEDIANO (TABLETS PORTRAIT / SMARTPHONES APASIONADOS)
 *
 * Sub-vista adaptativa optimizada para dispositivos con un ancho de pantalla entre 600 dp y 839 dp.
 * Reorganiza la portada del catálogo para equilibrar el espacio en pantallas intermedias mediante:
 * 1. Cabecera dispuesta en fila ([Row]) con el título de la sección e [InsigniaMideso].
 * 2. Banner de bienvenida e impacto social [HeroBanner] con navegación integrada hacia la sección de contacto.
 * 3. Malla cuadrícula de 2 columnas ([LazyVerticalGrid]) para exhibición de tarjetas de productos [TarjetaProductoItem].
 * 4. Pie de página informativo institucional con [FooterInformativo].
 *
 * @param navController Controlador de navegación de Jetpack Navigation para transiciones entre pantallas.
 * @param viewModel Instancia del ViewModel [MainViewModel] preparada para suministrar los datos del catálogo.
 */
@Suppress("UNUSED_PARAMETER")
@Composable
fun HomeScreenMediana(
    navController: NavController,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        // =========================================================================
        // 1. CABECERA PRINCIPAL CON ALINEACIÓN HORIZONTAL (TITULAR + INSIGNIA)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Catálogo Institucional",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            InsigniaMideso(
                texto = "Acreditación MIDESO - Ley 21.015"
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // =========================================================================
        // 2. HERO BANNER PRINCIPAL CON EVENTOS DE NAVEGACIÓN
        // =========================================================================
        HeroBanner(
            onVerTiendaClick = { /* Acción para ver tienda */ },
            onContactoClick = { navController.navigate("contacto") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // =========================================================================
        // 3. TÍTULO DE LA SECCIÓN DE PRODUCTOS DESTACADOS
        // =========================================================================
        Text(
            text = "Nuestros Productos Destacados",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        // =========================================================================
        // 4. REJILLA DE PRODUCTOS EN CUADRÍCULA (2 COLUMNAS)
        // =========================================================================
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 600.dp) // Altura contenida dentro del scroll general
        ) {
            items(6) { index ->
                TarjetaProductoItem(
                    nombre = "Producto Eco #${index + 1}",
                    categoria = "Inclusión",
                    precio = "$${(index + 1) * 2500}",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // =========================================================================
        // 5. PIE DE PÁGINA INFORMATIVO INSTITUCIONAL
        // =========================================================================
        FooterInformativo()
    }
}