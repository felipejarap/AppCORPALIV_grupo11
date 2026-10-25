package com.example.appcorpaliv_grupo11.ui.screens.auth

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appcorpaliv_grupo11.viewModel.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit = {}
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val ancho = maxWidth

        when {
            // Teléfonos (Compacta)
            ancho < 600.dp -> {
                LoginScreenCompacta(
                    authViewModel = authViewModel,
                    onLoginSuccess = onLoginSuccess
                )
            }
            // Tablets en vertical / Plegables (Mediana)
            ancho < 840.dp -> {
                LoginScreenMediana(
                    authViewModel = authViewModel,
                    onLoginSuccess = onLoginSuccess
                )
            }
            // Tablets en horizontal / Escritorio (Expandida)
            else -> {
                LoginScreenExpandida(
                    authViewModel = authViewModel,
                    onLoginSuccess = onLoginSuccess
                )
            }
        }
    }
}