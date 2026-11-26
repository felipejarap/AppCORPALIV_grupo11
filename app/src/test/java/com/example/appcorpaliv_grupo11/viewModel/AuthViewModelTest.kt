package com.example.appcorpaliv_grupo11.viewModel

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class AuthViewModelTest : StringSpec({

    "debe iniciar con sesion cerrada, campos vacios y sin error" {
        val viewModel = AuthViewModel()

        viewModel.usuarioInput shouldBe ""
        viewModel.passwordInput shouldBe ""
        viewModel.isLoggedIn.value shouldBe false
        viewModel.loginError.value shouldBe null
    }

    "debe actualizar usuario y contrasena y limpiar error previo" {
        val viewModel = AuthViewModel()
        viewModel.iniciarSesion()
        viewModel.loginError.value shouldBe "Por favor ingresa usuario y contraseña."

        viewModel.onUsuarioChanged("admin")
        viewModel.usuarioInput shouldBe "admin"
        viewModel.loginError.value shouldBe null

        viewModel.onPasswordChanged("1234")
        viewModel.passwordInput shouldBe "1234"
        viewModel.loginError.value shouldBe null
    }

    "debe mostrar error si usuario o contrasena estan en blanco" {
        val viewModel = AuthViewModel()
        viewModel.onUsuarioChanged("   ")
        viewModel.onPasswordChanged("")

        viewModel.iniciarSesion()

        viewModel.isLoggedIn.value shouldBe false
        viewModel.loginError.value shouldBe "Por favor ingresa usuario y contraseña."
    }

    "debe mostrar error cuando las credenciales son incorrectas" {
        val viewModel = AuthViewModel()
        viewModel.onUsuarioChanged("usuario_malo")
        viewModel.onPasswordChanged("clave_mala")

        viewModel.iniciarSesion()

        viewModel.isLoggedIn.value shouldBe false
        viewModel.loginError.value shouldBe "Credenciales incorrectas. Verifica tus datos."
    }

    "debe iniciar sesion con credenciales validas, limpiar campos y ejecutar callback" {
        val viewModel = AuthViewModel()
        var callbackEjecutado = false

        viewModel.onUsuarioChanged("  ADMIN  ")
        viewModel.onPasswordChanged("1234")

        viewModel.iniciarSesion { callbackEjecutado = true }

        viewModel.isLoggedIn.value shouldBe true
        viewModel.loginError.value shouldBe null
        viewModel.usuarioInput shouldBe ""
        viewModel.passwordInput shouldBe ""
        callbackEjecutado shouldBe true
    }

    "debe cerrar sesion y restablecer los campos de entrada" {
        val viewModel = AuthViewModel()
        viewModel.onUsuarioChanged("admin")
        viewModel.onPasswordChanged("1234")
        viewModel.iniciarSesion()

        viewModel.cerrarSesion()

        viewModel.isLoggedIn.value shouldBe false
        viewModel.usuarioInput shouldBe ""
        viewModel.passwordInput shouldBe ""
    }
})