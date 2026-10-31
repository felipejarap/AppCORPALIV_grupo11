package com.example.appcorpaliv_grupo11.ui.screens.contacto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import com.example.appcorpaliv_grupo11.ui.components.*
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

/**
 * PANTALLA DE FORMULARIO DE CONTACTO EN FORMATO EXPANDIDO (TABLETS EN LANDSCAPE / MONITORES)
 *
 * Sub-vista adaptativa diseñada para pantallas de más de 840 dp de ancho.
 * Distribuye el módulo de contacto en dos paneles horizontales simétricos mediante un [Row]:
 * 1. Panel Izquierdo (40% - weight 1f): Tarjeta con la información institucional de contacto.
 * 2. Panel Derecho (60% - weight 1.5f): Formulario interactivo de captura con validación en tiempo real.
 *
 * @param viewModel Instancia del ViewModel [ContactoViewModel] que gestiona los estados del formulario y la persistencia en Room.
 */
@Composable
fun ContactoScreenExpandida(viewModel: ContactoViewModel) {
    // Estado mutable para habilitar desplazamiento vertical en monitores o con pantalla dividida
    val scrollState = rememberScrollState()

    // Observación reactiva de los estados expuestos desde el StateFlow del ViewModel
    val isSubmitted by viewModel.isSubmitted.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(32.dp)
    ) {
        // Estructura en dos columnas horizontales paralelas con espaciado de 32 dp
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // =========================================================================
            // COLUMNA 1: INFORMACIÓN INSTITUCIONAL DE CONTACTO (PANEL IZQUIERDO)
            // =========================================================================
            TarjetaInfoContacto(
                modifier = Modifier.weight(1f)
            )

            // =========================================================================
            // COLUMNA 2: FORMULARIO INTERACTIVO / CONFIRMACIÓN DE ÉXITO (PANEL DERECHO)
            // =========================================================================
            Column(
                modifier = Modifier.weight(1.5f)
            ) {
                // Notificación animada que se activa tras el envío exitoso
                TarjetaExitoAnimada(
                    mostrarExito = isSubmitted,
                    onEnviarOtroMensaje = { viewModel.reiniciarFormulario() }
                )

                // Renderizado condicional del formulario de entrada
                if (!isSubmitted) {
                    // Campo 1: Nombre
                    CampoTextoInput(
                        value = viewModel.nombre,
                        onValueChange = { viewModel.onNombreChanged(it) },
                        label = "Nombre *",
                        icon = Icons.Default.Person,
                        error = viewModel.nombreError
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Campo 2: Correo electrónico
                    CampoTextoInput(
                        value = viewModel.email,
                        onValueChange = { viewModel.onEmailChanged(it) },
                        label = "Correo electrónico *",
                        icon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email,
                        error = viewModel.emailError
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Campo 3: Teléfono
                    CampoTextoInput(
                        value = viewModel.telefono,
                        onValueChange = { viewModel.onTelefonoChanged(it) },
                        label = "Teléfono (9 dígitos) *",
                        icon = Icons.Default.Phone,
                        keyboardType = KeyboardType.Number,
                        error = viewModel.telefonoError
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Campo 4: Asunto mediante menú desplegable
                    CampoDropdownInput(
                        value = viewModel.asunto,
                        onValueChange = { viewModel.onAsuntoChanged(it) },
                        label = "Asunto *",
                        opciones = viewModel.opcionesAsunto,
                        error = viewModel.asuntoError
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Campo 5: Mensaje de área de texto multilínea
                    CampoTextoInput(
                        value = viewModel.mensaje,
                        onValueChange = { viewModel.onMensajeChanged(it) },
                        label = "Mensaje *",
                        singleLine = false,
                        minLines = 4,
                        maxLines = 6,
                        error = viewModel.mensajeError
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Botón principal de acción con indicador de carga
                    BotonPrimario(
                        texto = "ENVIAR MENSAJE",
                        cargando = isLoading,
                        onClick = { viewModel.enviarFormulario {} }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Pie de página informativo institucional
        FooterInformativo()
    }
}