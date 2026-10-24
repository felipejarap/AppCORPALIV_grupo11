package com.example.appcorpaliv_grupo11.ui.screens.contacto

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.appcorpaliv_grupo11.data.AppDatabase
import com.example.appcorpaliv_grupo11.repository.ContactoRepository
import com.example.appcorpaliv_grupo11.ui.theme.AppCORPALIV_grupo11Theme
import com.example.appcorpaliv_grupo11.ui.utils.obtenerWindowSizeClass
import com.example.appcorpaliv_grupo11.viewModel.ContactoViewModel

@Composable
fun ContactoScreenMain(viewModel: ContactoViewModel) {
    val windowSizeClass = obtenerWindowSizeClass()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> ContactoScreenCompacta(viewModel = viewModel)
        WindowWidthSizeClass.Medium -> ContactoScreenMediana(viewModel = viewModel)
        WindowWidthSizeClass.Expanded -> ContactoScreenExpandida(viewModel = viewModel)
        else -> ContactoScreenCompacta(viewModel = viewModel)
    }
}

// --- FUNCIÓN HELPER PARA PREVIEWS ---
@Composable
private fun crearContactoViewModelDePrueba(): ContactoViewModel {
    val context = LocalContext.current
    return remember {
        val dbFalsa = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        val repoFalso = ContactoRepository(dbFalsa.contactoDao())
        ContactoViewModel(repoFalso)
    }
}

// --- PREVIEWS DE ANDROID STUDIO ---

@Preview(name = "1. Celular Vertical - Compact", widthDp = 360, heightDp = 800, showBackground = true, showSystemUi = true)
@Composable
fun PreviewContactoCompacta() {
    AppCORPALIV_grupo11Theme {
        ContactoScreenCompacta(viewModel = crearContactoViewModelDePrueba())
    }
}

@Preview(name = "2. Celular Horizontal - Medium", widthDp = 600, heightDp = 360, showBackground = true, showSystemUi = true)
@Composable
fun PreviewContactoMediana() {
    AppCORPALIV_grupo11Theme {
        ContactoScreenMediana(viewModel = crearContactoViewModelDePrueba())
    }
}

@Preview(name = "3. Tablet - Expanded", widthDp = 1024, heightDp = 768, showBackground = true, showSystemUi = true)
@Composable
fun PreviewContactoExpandida() {
    AppCORPALIV_grupo11Theme {
        ContactoScreenExpandida(viewModel = crearContactoViewModelDePrueba())
    }
}