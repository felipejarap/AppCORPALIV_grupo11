package com.example.appcorpaliv_grupo11.ui.screens.leyInclusion

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
 * PANTALLA DE LEY DE INCLUSIÓN EN FORMATO COMPACTO (SMARTPHONES PORTRAIT)
 *
 * Sub-vista adaptativa diseñada para teléfonos móviles en formato vertical.
 * Presenta el marco regulatorio de la Ley N° 21.015 y los mecanismos de cumplimiento alternativo
 * en un flujo continuo de una sola columna mediante [rememberScrollState]:
 * 1. Insignia de acreditación oficial MIDESO [InsigniaMideso].
 * 2. Encabezado principal del módulo.
 * 3. Tarjeta explicativa del marco legal [Card] con estilo de superficie variada.
 * 4. Tarjeta destacada de medida de cumplimiento alternativo mediante servicios Corpaliv.
 * 5. Botón primario de acción para solicitar asesoría técnica e iniciar el contacto.
 * 6. Pie de página informativo institucional [FooterInformativo].
 *
 * @param onContactarClick Callback emitido cuando el usuario presiona el botón de solicitud de asesoría.
 */
@Composable
fun LeyInclusionScreenCompacta(
    onContactarClick: () -> Unit = {}
) {
    // Contenedor principal vertical desplazable para garantizar legibilidad en pantallas reducidas
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
            texto = "Acreditación MIDESO - Ley N° 21.015"
        )

        Spacer(modifier = Modifier.height(20.dp))

        // =========================================================================
        // 2. TÍTULO PRINCIPAL DEL MÓDULO INFORMATIVO
        // =========================================================================
        Text(
            text = "Cumplimiento de Ley de Inclusión Laboral",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        // =========================================================================
        // 3. TARJETA INFORMATIVA: MARCO LEGAL REGULATORIO DE LA LEY 21.015
        // =========================================================================
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "¿En qué consiste la Ley 21.015?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Incentiva la inclusión de personas con discapacidad al mundo laboral, exigiendo a empresas con 100 o más trabajadores reservar al menos el 1% de sus puestos.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // =========================================================================
        // 4. TARJETA DESTACADA: MEDIDA DE CUMPLIMIENTO ALTERNATIVO (CORPALIV)
        // =========================================================================
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Medida de Cumplimiento Alternativo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Las empresas pueden cumplir celebrando contratos de prestación de servicios o compra de productos con instituciones acreditadas como Corpaliv.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // =========================================================================
        // 5. BOTÓN PRINCIPAL DE LLAMADO A LA ACCIÓN (SOLICITUD DE ASESORÍA)
        // =========================================================================
        Button(
            onClick = onContactarClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Solicitar Asesoría para Cumplimiento")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // =========================================================================
        // 6. PIE DE PÁGINA INFORMATIVO INSTITUCIONAL
        // =========================================================================
        FooterInformativo()
    }
}