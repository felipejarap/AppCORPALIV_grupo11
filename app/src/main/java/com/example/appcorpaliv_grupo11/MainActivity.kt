package com.example.appcorpaliv_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent // <-- ESTE ES EL IMPORT CLAVE QUE FALTA
import androidx.activity.enableEdgeToEdge
import com.example.appcorpaliv_grupo11.navigation.AppNavigation
import com.example.appcorpaliv_grupo11.ui.theme.AppCORPALIV_grupo11Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppCORPALIV_grupo11Theme {
                AppNavigation()
            }
        }
    }
}