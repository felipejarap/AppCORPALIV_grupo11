package com.example.appcorpaliv_grupo11.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * COMPONENTE REUTILIZABLE DE BOTÓN PRIMARIO
 *
 * Componente de interfaz de usuario desacoplado que encapsula un botón de estilo Material 3.
 * Soporta estados de carga asíncrona mediante un indicador de progreso circular (`CircularProgressIndicator`)
 * y deshabilitación dinámica para evitar múltiples toques accidentales por parte del usuario.
 *
 * @param texto Cadena de texto que se mostrará dentro del botón cuando no esté cargando.
 * @param onClick Acción o evento lambda a ejecutar al presionar el botón.
 * @param modifier Modificador de Compose para personalizar la maquetación externa desde la vista padre.
 * @param cargando Bandera booleana que activa la animación de carga en lugar del texto.
 * @param habilitado Bandera booleana que controla si el botón puede ser presionado.
 */
@Composable
fun BotonPrimario(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cargando: Boolean = false,
    habilitado: Boolean = true
) {
    // Componente base de botón con elevación y estilos estándar de Material 3
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth() // Ocupa todo el ancho horizontal disponible por defecto
            .height(50.dp), // Define una altura accesible y estándar de 50 dp para interacción táctil
        enabled = habilitado && !cargando, // El botón se inhabilita mientras esté cargando o deshabilitado explícitamente
        shape = RoundedCornerShape(12.dp) // Define bordes suavemente redondeados de 12 dp
    ) {
        // Evaluación condicional para alternar entre el indicador de carga y el contenido textual
        if (cargando) {
            // Indicador de progreso circular desplegado durante operaciones asíncronas
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary, // Utiliza el color de contraste sobre el fondo primario
                strokeWidth = 2.5.dp
            )
        } else {
            // Texto principal del botón en estado normal
            Text(
                text = texto,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}