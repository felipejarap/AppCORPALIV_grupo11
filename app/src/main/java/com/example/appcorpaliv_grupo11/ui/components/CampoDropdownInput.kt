package com.example.appcorpaliv_grupo11.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * COMPONENTE REUTILIZABLE DE MENÚ DESPLEGABLE (COMBOBOX / DROPDOWN INPUT)
 *
 * Componente desacoplado que encapsula un campo de texto desplegable con opciones predefinidas en Material 3.
 * Implementa [ExposedDropdownMenuBox] para la gestión visual del menú emergente y soporta
 * renderizado condicional de mensajes de error de validación.
 *
 * @param value Opción actualmente seleccionada.
 * @param onValueChange Callback que notifica hacia la vista superior cuando el usuario elige una opción.
 * @param label Texto descriptivo superior para el campo de entrada.
 * @param opciones Lista de cadenas con las alternativas seleccionables.
 * @param modifier Modificador para personalizar la maquetación externa desde el composable padre (primer parámetro opcional).
 * @param error Mensaje de error de validación (`null` si el campo es válido).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoDropdownInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    opciones: List<String>,
    modifier: Modifier = Modifier,
    error: String? = null
) {
    /** Estado interno mutable para controlar si la lista desplegable está abierta o cerrada. */
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        // Envoltorio contenedor para el menú emergente expuesto
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            // Campo de texto de solo lectura que despliega la opción seleccionada
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true, // Evita la edición manual por teclado para forzar la selección del menú
                label = { Text(label) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                isError = error != null,
                colors = OutlinedTextFieldDefaults.colors(),
                modifier = Modifier
                    // Uso de la sobrecarga moderna que elimina la advertencia de obsolescencia
                    .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                    .fillMaxWidth()
            )

            // Menú desplegable emergente que lista las opciones disponibles
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            onValueChange(opcion)
                            expanded = false // Cierra el menú emergente tras seleccionar una opción
                        }
                    )
                }
            }
        }

        // Renderizado condicional del mensaje de error de validación en la parte inferior del campo
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