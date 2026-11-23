package com.example.appcorpaliv_grupo11.repository

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

// Interfaz de ejemplo para simular un servicio de datos/API en la prueba unitaria
private interface ServicioPrueba {
    suspend fun obtenerItems(): List<String>
}

class EjemploRepositoryTest : StringSpec({

    "debe retornar los datos simulados desde el servicio" {
        runTest {
            val datosSimulados = listOf("Item 1", "Item 2")
            val servicioMock = mockk<ServicioPrueba>()

            coEvery { servicioMock.obtenerItems() } returns datosSimulados

            val resultado = servicioMock.obtenerItems()
            resultado shouldContainExactly listOf("Item 1", "Item 2")
        }
    }
})