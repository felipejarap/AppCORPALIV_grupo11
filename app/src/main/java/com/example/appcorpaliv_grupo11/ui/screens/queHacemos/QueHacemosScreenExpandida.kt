package com.example.appcorpaliv_grupo11.ui.screens.queHacemos

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
 * PANTALLA DE QUÉ HACEMOS EN FORMATO EXPANDIDO (TABLETS EN LANDSCAPE / MONITORES)
 *
 * Sub-vista adaptativa diseñada para pantallas de gran formato (ancho mayor a 840 dp).
 * Reorganiza el catálogo de servicios corporativos y oferta institucional en una matriz de 2x3:
 * 1. Cabecera principal en fila (Row) con titular en estilo MaterialTheme.typography.headlineLarge e InsigniaMideso.
 * 2. Primera fila de 3 tarjetas de servicios distribuidas con peso equitativo (weight(1f)):
 *    - Capacitaciones de Inclusión.
 *    - Experiencias de Inclusión (Destacado).
 *    - Análisis de Puesto de Trabajo.
 * 3. Segunda fila de 3 tarjetas de servicios distribuidas con peso equitativo (weight(1f)):
 *    - Acompañamiento de Inserción.
 *    - Accesibilidad Universal.
 *    - Políticas y Hunting Inclusivo (Destacado).
 * 4. Botón primario de acción alineado al extremo derecho (Alignment.End).
 * 5. Pie de página informativo institucional (FooterInformativo).
 *
 * @param onContactoClick Callback emitido cuando el usuario solicita mayor información o contacto directo.
 */
@Composable
fun QueHacemosScreenExpandida(
    onContactoClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp, vertical = 32.dp)
    ) {
        // =========================================================================
        // 1. CABECERA PRINCIPAL HORIZONTAL (TITULAR DESTACADO + INSIGNIA MIDESO)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Servicios para empresas y/o organizaciones",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ofrecemos experiencias y servicios que garantizan la exploración y asesoramiento para promover la inclusión laboral.",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(32.dp))

            // Insignia oficial de acreditación MIDESO
            InsigniaMideso(
                texto = "Acreditación MIDESO - Corpaliv"
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // =========================================================================
        // 2. FILA 1: MATRIZ DE SERVICIOS EN PARALELO (SERVICIOS 1 A 3)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Tarjeta 1: Capacitaciones
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "1. Capacitaciones de Inclusión",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Formación en temáticas de inclusión para influir positivamente en la percepción y entregar herramientas prácticas en el día a día.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Tarjeta 2: Experiencias (Destacado con PrimaryContainer)
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "2. Experiencias de Inclusión",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Instancia de vivencia práctica y trabajo colaborativo para pausar, vincularse e internalizar el valor de la mirada social.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Tarjeta 3: Análisis de Puesto
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "3. Análisis de Puesto de Trabajo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Estudio técnico del puesto laboral para definir funciones, responsabilidades y las calificaciones necesarias del postulante.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // =========================================================================
        // 3. FILA 2: MATRIZ DE SERVICIOS EN PARALELO (SERVICIOS 4 A 6)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Tarjeta 4: Acompañamiento
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "4. Acompañamiento de Inserción",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Proceso de ingreso laboral donde se aplica el análisis del puesto y se brinda acompañamiento continuo al trabajador.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // Tarjeta 5: Accesibilidad Universal
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "5. Accesibilidad Universal",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Evaluación del estado arquitectónico de las instalaciones para eliminar barreras y promover la libre movilidad.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Tarjeta 6: Políticas & Hunting (Destacado con PrimaryContainer)
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "6. Políticas & Hunting Inclusivo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Asesoría en políticas internas de inclusión y gestión integral en la búsqueda, reclutamiento y selección de candidatos.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // =========================================================================
        // 4. BOTÓN PRINCIPAL DE LLAMADO A LA ACCIÓN (ALINEADO A LA DERECHA)
        // =========================================================================
        Button(
            onClick = onContactoClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Ponerse en Contacto")
        }

        Spacer(modifier = Modifier.height(40.dp))

        // =========================================================================
        // 5. PIE DE PÁGINA INFORMATIVO INSTITUCIONAL
        // =========================================================================
        FooterInformativo()
    }
}