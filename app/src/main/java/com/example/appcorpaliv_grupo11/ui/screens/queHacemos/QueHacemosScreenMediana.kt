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
 * PANTALLA DE QUÉ HACEMOS EN FORMATO MEDIANO (TABLETS PORTRAIT / SMARTPHONES LANDSCAPE)
 *
 * Sub-vista adaptativa diseñada para dispositivos de formato intermedio (ancho entre 600 dp y 839 dp).
 * Organiza los 6 servicios corporativos e institucionales en una matriz fluida de 3 filas x 2 columnas:
 * 1. Encabezado en fila (Row) con titular en estilo MaterialTheme.typography.headlineMedium e InsigniaMideso.
 * 2. Fila 1 (2 columnas): Capacitaciones de Inclusión y Experiencias de Inclusión.
 * 3. Fila 2 (2 columnas): Análisis de Puesto de Trabajo y Acompañamiento de Inserción.
 * 4. Fila 3 (2 columnas): Accesibilidad Universal y Políticas y Hunting Inclusivo.
 * 5. Botón primario de acción alineado al extremo derecho (Alignment.End).
 * 6. Pie de página informativo institucional (FooterInformativo).
 *
 * @param onContactoClick Callback emitido cuando el usuario solicita mayor información o contacto directo.
 */
@Composable
fun QueHacemosScreenMediana(
    onContactoClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        // =========================================================================
        // 1. ENCABEZADO PRINCIPAL HORIZONTAL (TITULAR MEDIANO + INSIGNIA MIDESO)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Servicios para empresas y/o organizaciones",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ofrecemos experiencias y servicios que garantizan la exploración y asesoramiento para promover la inclusión laboral.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Insignia oficial de acreditación MIDESO
            InsigniaMideso(
                texto = "Acreditación MIDESO - Corpaliv"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // =========================================================================
        // 2. FILA 1: CAPACITACIONES Y EXPERIENCIAS DE INCLUSIÓN (2 COLUMNAS)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tarjeta 1: Capacitaciones (Estilo surfaceVariant)
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Capacitaciones de Inclusión",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Formación en temáticas de inclusión para influir positivamente en la percepción y entregar herramientas prácticas en la vida cotidiana.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Tarjeta 2: Experiencias (Estilo primaryContainer)
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Experiencias de Inclusión",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Instancia de vivencia práctica y trabajo colaborativo para pausar, vincularse e internalizar el valor de una mirada social.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // =========================================================================
        // 3. FILA 2: ANÁLISIS DE PUESTO Y ACOMPAÑAMIENTO LABORAL (2 COLUMNAS)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tarjeta 3: Análisis de Puesto (Estilo primaryContainer)
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Análisis de Puesto de Trabajo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Estudio y análisis técnico del puesto laboral para determinar funciones, responsabilidades y calificaciones del postulante.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Tarjeta 4: Acompañamiento (Estilo surfaceVariant)
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Acompañamiento de Inserción",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Instancia de ingreso laboral donde se aplica el análisis del puesto y se brinda acompañamiento continuo.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // =========================================================================
        // 4. FILA 3: ACCESIBILIDAD UNIVERSAL Y POLÍTICAS/HUNTING (2 COLUMNAS)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tarjeta 5: Accesibilidad Universal (Estilo surfaceVariant)
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Accesibilidad Universal",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Evaluación del estado arquitectónico de las edificaciones para eliminar barreras de infraestructura.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Tarjeta 6: Políticas & Hunting (Estilo primaryContainer)
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Políticas & Hunting Inclusivo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Apoyo en políticas de inclusión según normas vigentes y reclutamiento especializado de candidatos.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // =========================================================================
        // 5. BOTÓN PRINCIPAL DE LLAMADO A LA ACCIÓN (ALINEADO A LA DERECHA)
        // =========================================================================
        Button(
            onClick = onContactoClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Ponerse en Contacto")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // =========================================================================
        // 6. PIE DE PÁGINA INFORMATIVO INSTITUCIONAL
        // =========================================================================
        FooterInformativo()
    }
}