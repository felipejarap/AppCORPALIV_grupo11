package com.example.appcorpaliv_grupo11.ui.screens.quienesSomos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcorpaliv_grupo11.ui.components.FooterInformativo
import com.example.appcorpaliv_grupo11.ui.components.InsigniaMideso

/**
 * PANTALLA DE QUIÉNES SOMOS EN FORMATO COMPACTO (SMARTPHONES PORTRAIT)
 *
 * Sub-vista adaptativa diseñada para dispositivos móviles con ancho de pantalla menor a 600 dp.
 * Despliega la información institucional, historia y misión de Corpaliv en un flujo continuo de 1 columna:
 * 1. Insignia MIDESO de acreditación institucional (InsigniaMideso).
 * 2. Titular principal de la sección.
 * 3. Tarjeta informativa Sobre Corpaliv (Card) en tono surfaceVariant.
 * 4. Tarjeta destacada Nuestra Misión en tono primaryContainer.
 * 5. Tarjeta informativa Nuestro Equipo en tono surfaceVariant.
 * 6. Botón primario de acción para solicitar contacto directo.
 * 7. Pie de página informativo institucional (FooterInformativo).
 *
 * @param onContactoClick Callback emitido cuando el usuario presiona el botón para solicitar contacto.
 */
@Composable
fun QuienesSomosScreenCompacta(
    onContactoClick: () -> Unit = {}
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
            texto = "Acreditación MIDESO - Corpaliv"
        )

        Spacer(modifier = Modifier.height(20.dp))

        // =========================================================================
        // 2. TÍTULO PRINCIPAL DE LA SECCIÓN INSTITUCIONAL
        // =========================================================================
        Text(
            text = "¿Quiénes Somos?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        // =========================================================================
        // 3. TARJETA INFORMATIVA: RESEÑA SOBRE CORPALIV
        // =========================================================================
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Sobre Corpaliv",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Somos una corporación dedicada a mejorar la calidad de vida e integración de las personas con multidiscapacidad y sus familias.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // =========================================================================
        // 4. TARJETA DESTACADA: NUESTRA MISIÓN INSTITUCIONAL
        // =========================================================================
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Nuestra Misión",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Promover la inclusión plena, ofreciendo herramientas de apoyo, programas de capacitación y acompañamiento integral a familias e instituciones.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // =========================================================================
        // 5. TARJETA INFORMATIVA: NUESTRO EQUIPO DE TRABAJO
        // =========================================================================
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Nuestro Equipo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Contamos con un equipo multidisciplinario de profesionales comprometidos con el desarrollo integral y la defensa de los derechos de las personas con discapacidad.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // =========================================================================
        // 6. BOTÓN PRINCIPAL DE LLAMADO A LA ACCIÓN (CONTACTO)
        // =========================================================================
        Button(
            onClick = onContactoClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ponerse en Contacto")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // =========================================================================
        // 7. PIE DE PÁGINA INFORMATIVO INSTITUCIONAL
        // =========================================================================
        FooterInformativo()
    }
}