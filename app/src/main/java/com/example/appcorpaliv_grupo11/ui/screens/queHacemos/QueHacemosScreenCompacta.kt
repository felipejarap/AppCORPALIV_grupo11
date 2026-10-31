package com.example.appcorpaliv_grupo11.ui.screens.queHacemos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcorpaliv_grupo11.ui.components.FooterInformativo
import com.example.appcorpaliv_grupo11.ui.components.InsigniaMideso

/**
 * PANTALLA DE QUÉ HACEMOS EN FORMATO COMPACTO (SMARTPHONES PORTRAIT)
 *
 * Sub-vista adaptativa diseñada para dispositivos móviles con ancho de pantalla menor a 600 dp.
 * Presenta el catálogo de servicios corporativos y experiencias de inclusión en una sola columna perezosa (LazyColumn):
 * 1. Insignia MIDESO de acreditación institucional (InsigniaMideso).
 * 2. Encabezado e introducción explicativa sobre el propósito social.
 * 3. Tarjeta de Capacitaciones de Inclusión (Card).
 * 4. Tarjeta de Experiencias de Inclusión con resaltado visual.
 * 5. Tarjeta de Análisis de Puesto de Trabajo.
 * 6. Tarjeta de Acompañamiento de Inserción Laboral.
 * 7. Tarjeta de Evaluación de Accesibilidad Universal.
 * 8. Tarjeta de Políticas de Inclusión y Hunting Inclusivo.
 * 9. Botón primario de acción para solicitar mayor información.
 * 10. Pie de página informativo institucional (FooterInformativo).
 *
 * @param onContactoClick Callback emitido cuando el usuario presiona el botón para solicitar contacto.
 */
@Composable
fun QueHacemosScreenCompacta(
    onContactoClick: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // =========================================================================
        // 1. INSIGNIA DE ACREDITACIÓN INSTITUCIONAL MIDESO
        // =========================================================================
        item {
            InsigniaMideso(
                texto = "Acreditación MIDESO - Corpaliv"
            )
        }

        // =========================================================================
        // 2. ENCABEZADO PRINCIPAL Y TEXTO INTRODUCTORIO
        // =========================================================================
        item {
            Column {
                Text(
                    text = "Servicios para empresas y/o organizaciones",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Nuestra institución se enfoca en concientizar y educar a nuestros aliados y a la sociedad. Por ello, ofrecemos una variedad de experiencias y servicios que garantizan el conocimiento, la exploración y el asesoramiento necesarios para promover de manera equitativa la inclusión laboral de personas con discapacidad múltiple.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Experiencias y Servicios",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // =========================================================================
        // 3. CAPACITACIONES DE INCLUSIÓN
        // =========================================================================
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Capacitaciones de Inclusión",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Formación y capacitación en temáticas en torno a la inclusión de personas en situación de discapacidad. Cómo influir en la percepción y trato hacia las personas proporcionando herramientas prácticas para incorporar de manera efectiva en la vida cotidiana.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // =========================================================================
        // 4. EXPERIENCIAS DE INCLUSIÓN
        // =========================================================================
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Experiencias de Inclusión",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Instancia de vivencia práctica y de trabajo colaborativo inclusivo con el fin de compartir una pausa de vinculación e interacción para poder internalizar el valor de una mirada social.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // =========================================================================
        // 5. ANÁLISIS DE PUESTO DE TRABAJO
        // =========================================================================
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Análisis de Puesto de Trabajo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Estudio y análisis de un puesto de trabajo para determinar funciones, actividades y responsabilidades, y definir las calificaciones necesarias del candidato para su desempeño.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // =========================================================================
        // 6. ACOMPAÑAMIENTO DE INSERCIÓN LABORAL
        // =========================================================================
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Acompañamiento de Inserción Laboral",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Instancia de ingreso laboral del postulante, en que se recomienda la aplicación del análisis de puesto de trabajo y el acompañamiento laboral.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // =========================================================================
        // 7. EVALUACIÓN DE ACCESIBILIDAD UNIVERSAL
        // =========================================================================
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Evaluación de Accesibilidad Universal",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Evaluación del estado arquitectónico de una edificación para fomentar la eliminación de barreras de infraestructura y promover la accesibilidad universal.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // =========================================================================
        // 8. POLÍTICAS DE INCLUSIÓN Y HUNTING INCLUSIVO
        // =========================================================================
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Políticas de Inclusión y Hunting Inclusivo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Reconocimiento de la organización y apoyo en la elaboración de políticas de inclusión según la norma. Además, evaluación de requerimientos en reclutamiento y selección de candidatos.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // =========================================================================
        // 9. BOTÓN PRINCIPAL DE LLAMADO A LA ACCIÓN
        // =========================================================================
        item {
            Button(
                onClick = onContactoClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ponerse en Contacto")
            }
        }

        // =========================================================================
        // 10. PIE DE PÁGINA INFORMATIVO INSTITUCIONAL
        // =========================================================================
        item {
            FooterInformativo()
        }
    }
}