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
 * PANTALLA DE LEY DE INCLUSIÓN EN FORMATO EXPANDIDO (TABLETS EN LANDSCAPE / MONITORES)
 *
 * Sub-vista adaptativa diseñada para pantallas de más de 840 dp de ancho.
 * Reorganiza la información normativa y la oferta de cumplimiento corporativo mediante:
 * 1. Cabecera horizontal ([Row]) con título principal en tipografía destacada e [InsigniaMideso].
 * 2. Distribución en 3 columnas horizontales paralelas ([Row] de [Card] con peso equitativo `weight(1f)`):
 *    - Columna 1: Reserva del 1% (Marco Legal).
 *    - Columna 2: Medida de Cumplimiento Alternativo (Solución Corpaliv).
 *    - Columna 3: Retorno e Impacto Social (Talleres y Rehabilitación).
 * 3. Botón de llamado a la acción alistado a la derecha (`Alignment.End`).
 * 4. Pie de página informativo institucional [FooterInformativo].
 *
 * @param onContactarClick Callback emitido cuando el usuario solicita contacto comercial o asesoría técnica.
 */
@Composable
fun LeyInclusionScreenExpandida(
    onContactarClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp, vertical = 32.dp)
    ) {
        // =========================================================================
        // 1. CABECERA PRINCIPAL HORIZONTAL (TITULAR DE IMPACTO + INSIGNIA MIDESO)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Marco de Inclusión Laboral y Acreditación MIDESO",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Soluciones corporativas sustentables bajo la Ley N° 21.015",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(32.dp))

            // Insignia oficial MIDESO ubicada en el extremo derecho de la cabecera
            InsigniaMideso(
                texto = "Acreditación MIDESO - Ley 21.015"
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // =========================================================================
        // 2. PANEL DE TRES COLUMNAS HORIZONTALES (PILARES DEL CUMPLIMIENTO)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Pilar 1: Marco Legal de la Ley
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("1. Reserva 1%", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Requisito obligatorio de contratación para empresas privadas y organismos públicos de 100 o más trabajadores.", fontSize = 14.sp)
                }
            }

            // Pilar 2: Medida Alternativa
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("2. Medida Alternativa", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Compra de productos e insumos corporativos a instituciones acreditadas por el Ministerio de Desarrollo Social.", fontSize = 14.sp)
                }
            }

            // Pilar 3: Retorno e Impacto Social
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("3. Impacto Social", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Financiamiento directo para los talleres pedagógicos y terapias de rehabilitación en Corpaliv.", fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // =========================================================================
        // 3. BOTÓN DE ACCIÓN ALINEADO A LA DERECHA
        // =========================================================================
        Button(
            onClick = onContactarClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Contactar Asesor Corpaliv")
        }

        Spacer(modifier = Modifier.height(40.dp))

        // =========================================================================
        // 4. PIE DE PÁGINA INFORMATIVO INSTITUCIONAL
        // =========================================================================
        FooterInformativo()
    }
}