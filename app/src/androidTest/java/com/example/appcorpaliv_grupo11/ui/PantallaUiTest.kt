package com.example.appcorpaliv_grupo11.ui

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class PantallaUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun texto_debe_aparecer_en_pantalla() {
        composeRule.setContent {
            Text(text = "App CORPALIV")
        }

        composeRule.onNodeWithText("App CORPALIV").assertIsDisplayed()
    }
}