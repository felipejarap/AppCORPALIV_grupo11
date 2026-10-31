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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.appcorpaliv_grupo11.ui.components.FooterInformativo
import com.example.appcorpaliv_grupo11.ui.components.HeroBanner
import com.example.appcorpaliv_grupo11.ui.components.InsigniaMideso
import com.example.appcorpaliv_grupo11.ui.components.TarjetaProductoItem
import com.example.appcorpaliv_grupo11.viewModel.MainViewModel

/**
 * PANTALLA PRINCIPAL DE INICIO EN FORMATO EXPANDIDO (TABLETS EN LANDSCAPE / MONITORES)
 *
 * Sub-vista adaptativa diseñada para pantallas de más de 840 dp de ancho.
 * Reorganiza la portada del catálogo para aprovechar el espacio horizontal mediante:
 * 1. Cabecera alineada en fila ([Row]) con el título de la sección e [InsigniaMideso].
 * 2. Banner principal de bienvenida [HeroBanner] con navegación integrada.
 * 3. Malla cuadrícula interactiva de 4 columnas ([LazyVerticalGrid]) para exhibir tarjetas de productos [TarjetaProductoItem].
 * 4. Pie de página informativo institucional con [FooterInformativo].
 *
 * @param navController Controlador de navegación de Jetpack Navigation para transiciones entre pantallas.
 * @param viewModel Instancia del ViewModel [MainViewModel] preparada para suministrar los datos del catálogo.
 */
@Suppress("UNUSED_PARAMETER")
@Composable
fun HomeScreenExpandida(
    navController: NavController,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp, vertical = 32.dp)
    ) {
        // =========================================================================
        // 1. CABECERA PRINCIPAL CON ALINEACIÓN HORIZONTAL (TITULAR + INSIGNIA)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Catálogo de Productos y Servicios",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Apoya nuestros programas sociales comprando productos inclusivos.",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            // Insignia acreditativa de la Ley 21.015 en el extremo derecho
            InsigniaMideso(
                texto = "Acreditación MIDESO - Ley 21.015"
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // =========================================================================
        // 2. HERO BANNER PRINCIPAL CON EVENTOS DE NAVEGACIÓN
        // =========================================================================
        HeroBanner(
            onVerTiendaClick = { /* Acción ver tienda */ },
            onContactoClick = { navController.navigate("contacto") }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // =========================================================================
        // 3. SECCIÓN DE PRODUCTOS EN MALLA MULTICOLUMNA (4 COLUMNAS)
        // =========================================================================
        Text(
            text = "Nuestros Productos Destacados",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cuadrícula vertical optimizada con tope de altura para evitar conflictos con el scroll general
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 700.dp)
        ) {
            items(8) { index ->
                TarjetaProductoItem(
                    nombre = "Producto Inclusivo #${index + 1}",
                    categoria = "Empresas",
                    precio = "$${(index + 1) * 3500}",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // =========================================================================
        // 4. PIE DE PÁGINA INFORMATIVO INSTITUCIONAL
        // =========================================================================
        FooterInformativo()
    }
}