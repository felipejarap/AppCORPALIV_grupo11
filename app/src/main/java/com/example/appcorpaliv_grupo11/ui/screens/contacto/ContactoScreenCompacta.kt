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
 * PANTALLA DE FORMULARIO DE CONTACTO EN FORMATO COMPACTO (SMARTPHONES PORTRAIT)
 *
 * Sub-vista adaptativa para smartphones en formato vertical. Reúne en un único flujo
 * desplazable ([rememberScrollState]) la tarjeta de canales de contacto, el formulario
 * dinámico de captura con validación en tiempo real y retroalimentación de estado asíncrono
 * mediante [ContactoViewModel], [TarjetaExitoAnimada] y [FooterInformativo].
 *
 * @param viewModel Instancia del ViewModel [ContactoViewModel] que gestiona los estados del formulario y Room.
 */
@Composable
fun ContactoScreenCompacta(viewModel: ContactoViewModel) {
    // Estado mutable de desplazamiento vertical para evitar colisiones con el teclado virtual
    val scrollState = rememberScrollState()

    // Suscripción reactiva a los estados expuestos por el StateFlow del ViewModel
    val isSubmitted by viewModel.isSubmitted.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState() // Observa el estado de persistencia asíncrona

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        // =========================================================================
        // 1. TARJETA SUPERIOR DE INFORMACIÓN INSTITUCIONAL DE CONTACTO
        // =========================================================================
        TarjetaInfoContacto(
            modifier = Modifier.padding(16.dp)
        )

        // =========================================================================
        // 2. CONTENEDOR DE FORMULARIO / TARJETA DE ÉXITO ANIMADA
        // =========================================================================
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Notificación animada que se despliega automáticamente tras un envío exitoso
            TarjetaExitoAnimada(
                mostrarExito = isSubmitted,
                onEnviarOtroMensaje = { viewModel.reiniciarFormulario() }
            )

            // Renderizado condicional: Muestra los campos del formulario solo si no se ha enviado con éxito
            if (!isSubmitted) {
                // Campo 1: Nombre completo
                CampoTextoInput(
                    value = viewModel.nombre,
                    onValueChange = { viewModel.onNombreChanged(it) },
                    label = "Nombre *",
                    icon = Icons.Default.Person,
                    error = viewModel.nombreError
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Campo 2: Correo electrónico con validación de tipo de teclado
                CampoTextoInput(
                    value = viewModel.email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = "Correo electrónico *",
                    icon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email,
                    error = viewModel.emailError
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Campo 3: Teléfono formateado a 9 dígitos
                CampoTextoInput(
                    value = viewModel.telefono,
                    onValueChange = { viewModel.onTelefonoChanged(it) },
                    label = "Teléfono (9 dígitos) *",
                    icon = Icons.Default.Phone,
                    keyboardType = KeyboardType.Number,
                    error = viewModel.telefonoError
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Campo 4: Menú desplegable para selección de asunto
                CampoDropdownInput(
                    value = viewModel.asunto,
                    onValueChange = { viewModel.onAsuntoChanged(it) },
                    label = "Asunto *",
                    opciones = viewModel.opcionesAsunto,
                    error = viewModel.asuntoError
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Campo 5: Mensaje multilínea con límites visuales
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

                // Botón principal de acción con soporte para indicador de carga (cargando = isLoading)
                BotonPrimario(
                    texto = "ENVIAR MENSAJE",
                    cargando = isLoading,
                    onClick = { viewModel.enviarFormulario {} }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // =========================================================================
        // 3. PIE DE PÁGINA INFORMATIVO
        // =========================================================================
        FooterInformativo()
    }
}