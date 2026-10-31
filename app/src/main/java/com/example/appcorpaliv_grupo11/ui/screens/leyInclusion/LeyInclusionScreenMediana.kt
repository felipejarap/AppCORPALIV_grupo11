package com.example.appcorpaliv_grupo11.ui.screens.leyInclusion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcorpaliv_grupo11.ui.components.FooterInformativo
import com.example.appcorpaliv_grupo11.ui.components.InsigniaMideso

/**
 * PANTALLA DE LEY DE INCLUSIÓN EN FORMATO MEDIANO (TABLETS PORTRAIT / SMARTPHONES APASIONADOS)
 *
 * Sub-vista adaptativa optimizada para dispositivos con un ancho de pantalla entre 600 dp y 839 dp.
 * Reorganiza la información legal y corporativa de la Ley N° 21.015 en un formato de dos columnas:
 * 1. Cabecera en fila ([Row]) con el título principal e [InsigniaMideso] alineados horizontalmente.
 * 2. Distribución horizontal en 2 columnas paralelas ([Row] con dos [Card] de peso equitativo `weight(1f)`):
 *    - Columna 1: Marco Legal y reserva obligatoria del 1%.
 *    - Columna 2: Cumplimiento Alternativo mediante donaciones o contratación de servicios Corpaliv.
 * 3. Botón de acción alineado al extremo derecho (`Alignment.End`) para solicitar asesoría técnica.
 * 4. Pie de página informativo institucional con [FooterInformativo].
 *
 * @param onContactarClick Callback emitido cuando el usuario solicita asesoría o contacto directo.
 */
@Composable
fun LeyInclusionScreenMediana(
    onContactarClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        // =========================================================================
        // 1. CABECERA PRINCIPAL HORIZONTAL (TÍTULO DE LA LEY + INSIGNIA MIDESO)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ley N° 21.015 - Inclusión Laboral",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Insignia acreditativa oficial de la Ley N° 21.015
            InsigniaMideso(
                texto = "Acreditación MIDESO - Ley 21.015"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // =========================================================================
        // 2. PANEL DE DOS COLUMNAS HORIZONTALES (MARCO LEGAL VS MEDIDA ALTERNATIVA)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Columna 1: Explicación del Marco Legal Regulatorio
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Marco Legal",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Establece la reserva del 1% para personas con discapacidad o asignatarias de pensión de invalidez en empresas de 100+ trabajadores.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Columna 2: Mecanismo de Cumplimiento por Donaciones y Servicios Corpaliv
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Donaciones y Servicios",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Al adquirir productos o contratar servicios de Corpaliv, tu empresa cumple válidamente la normativa laboral de inclusión.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // =========================================================================
        // 3. BOTÓN PRINCIPAL DE LLAMADO A LA ACCIÓN (ALINEADO A LA DERECHA)
        // =========================================================================
        Button(
            onClick = onContactarClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Solicitar Asesoría de Cumplimiento")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // =========================================================================
        // 4. PIE DE PÁGINA INFORMATIVO INSTITUCIONAL
        // =========================================================================
        FooterInformativo()
    }
}