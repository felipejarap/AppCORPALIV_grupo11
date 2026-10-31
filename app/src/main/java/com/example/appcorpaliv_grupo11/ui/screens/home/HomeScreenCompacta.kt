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

/**
 * PANTALLA PRINCIPAL DE INICIO EN FORMATO COMPACTO (SMARTPHONES PORTRAIT)
 *
 * Sub-vista adaptativa diseñada para dispositivos móviles en orientación vertical.
 * Integra de forma secuencial en una columna desplazable ([rememberScrollState]):
 * 1. Acreditación institucional mediante [InsigniaMideso].
 * 2. Banner principal de bienvenida [HeroBanner] con navegación directa a contacto.
 * 3. Carrusel horizontal optimizado ([LazyRow]) para exhibición de catálogo con [TarjetaProductoItem].
 * 4. Pie de página informativo institucional con [FooterInformativo].
 *
 * @param navController Controlador de navegación para ejecutar transiciones hacia otras rutas del sistema.
 * @param viewModel Instancia del ViewModel [MainViewModel] preparada para la integración con el catálogo.
 */
@Suppress("UNUSED_PARAMETER")
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
        // =========================================================================
        // 1. INSIGNIA DE ACREDITACIÓN INSTITUCIONAL MIDESO
        // =========================================================================
        InsigniaMideso(
            texto = "Acreditación MIDESO - Ley 21.015"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // =========================================================================
        // 2. HERO BANNER PRINCIPAL CON EVENTOS DE NAVEGACIÓN
        // =========================================================================
        HeroBanner(
            onVerTiendaClick = {
                // Acción requerida de la tienda o catálogo
            },
            onContactoClick = {
                navController.navigate("contacto") // Transición hacia la pantalla de contacto
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // =========================================================================
        // 3. ENCABEZADO DE LA SECCIÓN DE CATÁLOGO DE PRODUCTOS
        // =========================================================================
        Text(
            text = "Nuestros Productos Destacados",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        // =========================================================================
        // 4. CARRUSEL HORIZONTAL EFICIENTE DE PRODUCTOS (LAZYROW)
        // =========================================================================
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

        // =========================================================================
        // 5. PIE DE PÁGINA INFORMATIVO INSTITUCIONAL
        // =========================================================================
        FooterInformativo()
    }
}