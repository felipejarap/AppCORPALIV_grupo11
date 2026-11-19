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
 * PANTALLA DE FORMULARIO DE CONTACTO EN FORMATO MEDIANO (TABLETS PORTRAIT / SMARTPHONES APAPAISADOS)
 *
 * Sub-vista adaptativa optimizada para dispositivos con un ancho de pantalla entre 600 dp y 839 dp.
 * Organiza la vista en un contenedor horizontal [Row] de dos columnas paralelas equilibradas:
 * 1. Panel Izquierdo (45% - weight 1f): Tarjeta de información institucional [TarjetaInfoContacto].
 * 2. Panel Derecho (55% - weight 1.2f): Formulario interactivo o tarjeta de éxito [TarjetaExitoAnimada].
 *
 * @param viewModel Instancia del ViewModel [ContactoViewModel] que gestiona la lógica de validación y la persistencia en Room.
 */
@Composable
fun ContactoScreenMediana(viewModel: ContactoViewModel) {
    // Estado mutable para gestionar el desplazamiento vertical en pantalla
    val scrollState = rememberScrollState()

    // Suscripción reactiva a los estados expuestos desde el StateFlow del ViewModel
    val isSubmitted by viewModel.isSubmitted.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(24.dp)
    ) {
        // Estructura horizontal de dos columnas con un espaciado intermedio de 24 dp
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // =========================================================================
            // COLUMNA 1: INFORMACIÓN INSTITUCIONAL DE CONTACTO (IZQUIERDA)
            // =========================================================================
            TarjetaInfoContacto(
                modifier = Modifier.weight(1f)
            )

            // =========================================================================
            // COLUMNA 2: FORMULARIO INTERACTIVO / TARJETA DE ÉXITO (DERECHA)
            // =========================================================================
            Column(
                modifier = Modifier.weight(1.2f)
            ) {
                // Confirmación animada post-envío
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

                    // Campo 3: Teléfono (9 dígitos)
                    CampoTextoInput(
                        value = viewModel.telefono,
                        onValueChange = { viewModel.onTelefonoChanged(it) },
                        label = "Teléfono (9 dígitos) *",
                        icon = Icons.Default.Phone,
                        keyboardType = KeyboardType.Number,
                        error = viewModel.telefonoError
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Campo 4: Asunto
                    CampoDropdownInput(
                        value = viewModel.asunto,
                        onValueChange = { viewModel.onAsuntoChanged(it) },
                        label = "Asunto *",
                        opciones = viewModel.opcionesAsunto,
                        error = viewModel.asuntoError
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Campo 5: Mensaje multilínea
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

                    // Botón principal de acción
                    BotonPrimario(
                        texto = "ENVIAR MENSAJE",
                        cargando = isLoading,
                        onClick = { viewModel.enviarFormulario {} }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Pie de página informativo institucional
        FooterInformativo()
    }
}