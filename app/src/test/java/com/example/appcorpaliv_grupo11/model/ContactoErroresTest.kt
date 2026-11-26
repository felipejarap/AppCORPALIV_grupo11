package com.example.appcorpaliv_grupo11.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ContactoErroresTest : StringSpec({

    "debe inicializar todos los errores en null por defecto y permitir asignarlos" {
        val vacio = ContactoErrores()
        vacio.nombreError shouldBe null
        vacio.emailError shouldBe null
        vacio.telefonoError shouldBe null
        vacio.asuntoError shouldBe null
        vacio.mensajeError shouldBe null
        vacio.generalError shouldBe null

        val conErrores = ContactoErrores(
            nombreError = "Requerido",
            emailError = "Correo invalido",
            telefonoError = "Telefono invalido",
            asuntoError = "Asunto requerido",
            mensajeError = "Mensaje vacio",
            generalError = "Error general"
        )
        conErrores.nombreError shouldBe "Requerido"
        conErrores.emailError shouldBe "Correo invalido"
        conErrores.telefonoError shouldBe "Telefono invalido"
        conErrores.asuntoError shouldBe "Asunto requerido"
        conErrores.mensajeError shouldBe "Mensaje vacio"
        conErrores.generalError shouldBe "Error general"
    }
})