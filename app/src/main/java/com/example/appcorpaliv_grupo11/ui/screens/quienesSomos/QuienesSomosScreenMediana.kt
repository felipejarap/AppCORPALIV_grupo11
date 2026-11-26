package com.example.appcorpaliv_grupo11.ui.screens.quienesSomos

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
 * PANTALLA DE QUIÉNES SOMOS EN FORMATO MEDIANO (TABLETS PORTRAIT / SMARTPHONES LANDSCAPE)
 *
 * Sub-vista adaptativa diseñada para dispositivos de formato intermedio (ancho entre 600 dp y 839 dp).
 * Distribuye la información institucional de Corpaliv en un esquema híbrido de dos niveles:
 * 1. Cabecera en fila (Row) con titular en estilo MaterialTheme.typography.headlineMedium e InsigniaMideso.
 * 2. Nivel 1: Fila paralela de 2 columnas con peso equitativo (weight(1f)) para "Sobre Corpaliv" y "Nuestra Misión".
 * 3. Nivel 2: Tarjeta a ancho completo (fillMaxWidth) destacando a "Nuestro Equipo".
 * 4. Botón primario de acción alineado al extremo derecho (Alignment.End).
 * 5. Pie de página informativo institucional (FooterInformativo).
 *
 * @param onContactoClick Callback emitido cuando el usuario solicita mayor información o contacto directo.
 */
@Composable
fun QuienesSomosScreenMediana(
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
            Text(
                text = "Conoce más sobre Corpaliv",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Insignia oficial de acreditación MIDESO
            InsigniaMideso(
                texto = "Acreditación MIDESO - Corpaliv"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // =========================================================================
        // 2. NIVEL 1: FILA DE DOS COLUMNAS (SOBRE CORPALIV VS NUESTRA MISIÓN)
        // =========================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tarjeta Sobre Corpaliv (Estilo surfaceVariant)
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Sobre Corpaliv",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
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

            // Tarjeta Misión (Estilo primaryContainer)
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Nuestra Misión",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        // =========================================================================
        // 3. NIVEL 2: TARJETA A ANCHO COMPLETO (NUESTRO EQUIPO)
        // =========================================================================
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Nuestro Equipo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
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
        // 4. BOTÓN PRINCIPAL DE LLAMADO A LA ACCIÓN (ALINEADO A LA DERECHA)
        // =========================================================================
        Button(
            onClick = onContactoClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Ponerse en Contacto")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // =========================================================================
        // 5. PIE DE PÁGINA INFORMATIVO INSTITUCIONAL
        // =========================================================================
        FooterInformativo()
    }
}