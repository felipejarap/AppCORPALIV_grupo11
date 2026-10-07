package com.example.appcorpaliv_grupo11.ui.screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcorpaliv_grupo11.ui.theme.AppCORPALIV_grupo11Theme
import com.example.appcorpaliv_grupo11.ui.utils.obtenerWindowSizeClass
import com.example.appcorpaliv_grupo11.viewmodel.MainViewModel

@Composable
fun HomeScreenMain(viewModel: MainViewModel) {
    val windowSizeClass = obtenerWindowSizeClass()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> HomeScreenCompacta(viewModel = viewModel)
        WindowWidthSizeClass.Medium -> HomeScreenMediana(viewModel = viewModel)
        WindowWidthSizeClass.Expanded -> HomeScreenExpandida(viewModel = viewModel)
        else -> HomeScreenCompacta(viewModel = viewModel)
    }
}

@Preview(name = "1. Celular Vertical - Compact", widthDp = 360, heightDp = 800, showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainUnicoCompacta() {
    AppCORPALIV_grupo11Theme {
        HomeScreenCompacta(viewModel = viewModel())
    }
}

@Preview(name = "2. Celular Horizontal - Medium", widthDp = 600, heightDp = 360, showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainUnicoMediana() {
    AppCORPALIV_grupo11Theme {
        HomeScreenMediana(viewModel = viewModel())
    }
}

@Preview(name = "3. Tablet - Expanded", widthDp = 1024, heightDp = 768, showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainUnicoExpandida() {
    AppCORPALIV_grupo11Theme {
        HomeScreenExpandida(viewModel = viewModel())
    }
}