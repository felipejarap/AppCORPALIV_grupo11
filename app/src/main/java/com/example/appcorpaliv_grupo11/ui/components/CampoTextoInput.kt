package com.example.appcorpaliv_grupo11.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * COMPONENTE REUTILIZABLE DE CAMPO DE TEXTO (TEXT INPUT / OUTLINED TEXT FIELD)
 *
 * Componente atómico de entrada de datos desacoplado que encapsula un [OutlinedTextField] de Material 3.
 * Soporta la inclusión opcional de un ícono descriptivo a la izquierda (`leadingIcon`),
 * configuración personalizada del teclado suave de Android (`KeyboardType`), campos multilínea
 * y despliegue dinámico de mensajes de error de validación.
 *
 * @param value Texto actual ingresado en el campo.
 * @param onValueChange Callback que notifica las modificaciones del texto hacia el nivel superior.
 * @param label Etiqueta flotante superior que describe el propósito del campo.
 * @param modifier Modificador para personalizar la maquetación externa (reubicado como primer parámetro opcional).
 * @param icon Ícono vectorial opcional a dibujar en la parte izquierda del campo (`leadingIcon`).
 * @param error Texto del mensaje de error de validación (`null` si el estado es válido).
 * @param singleLine Restringe la entrada a una sola línea de texto cuando está en `true`.
 * @param minLines Número mínimo de líneas visibles para campos extensos (como comentarios o mensajes).
 * @param maxLines Número máximo de líneas visibles antes de activar el desplazamiento vertical.
 * @param keyboardType Tipo de teclado virtual a solicitar (Texto, Teléfono, Correo, Número, etc.).
 */
@Composable
fun CampoTextoInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier, // <- Reordenado: Primer parámetro opcional según las guías de estilo de Compose
    icon: ImageVector? = null,
    error: String? = null,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Campo de entrada estilizado con bordes según las guías de Material 3
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            leadingIcon = if (icon != null) {
                { Icon(imageVector = icon, contentDescription = label) }
            } else null,
            isError = error != null,
            singleLine = singleLine,
            minLines = minLines,
            maxLines = maxLines,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth()
        )

        // Renderizado condicional de la etiqueta de error de validación inferior
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}