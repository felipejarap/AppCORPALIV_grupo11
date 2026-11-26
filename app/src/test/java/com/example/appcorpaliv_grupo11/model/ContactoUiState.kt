package com.example.appcorpaliv_grupo11.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ContactoUiStateTest : StringSpec({

    "debe inicializar ContactoUiState con valores por defecto y permitir copias actualizadas" {
        val inicial = ContactoUiState()

        inicial.nombre shouldBe ""
        inicial.email shouldBe ""
        inicial.telefono shouldBe ""
        inicial.asunto shouldBe ""
        inicial.mensaje shouldBe ""
        inicial.errores shouldBe ContactoErrores()
        inicial.isSubmitted shouldBe false

        val actualizado = inicial.copy(
            nombre = "Camila",
            email = "camila@correo.cl",
            telefono = "987654321",
            asunto = "Quiero colaborar",
            mensaje = "Consulta de prueba",
            errores = ContactoErrores(nombreError = null),
            isSubmitted = true
        )

        actualizado.nombre shouldBe "Camila"
        actualizado.email shouldBe "camila@correo.cl"
        actualizado.telefono shouldBe "987654321"
        actualizado.asunto shouldBe "Quiero colaborar"
        actualizado.mensaje shouldBe "Consulta de prueba"
        actualizado.isSubmitted shouldBe true
    }
})