package com.example.appcorpaliv_grupo11.viewModel

import android.util.Log
import android.util.Patterns
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import com.example.appcorpaliv_grupo11.repository.ContactoRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.regex.Pattern

private fun asegurarPatternsEmailAddress() {
    val patternValido: Pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    val field: Field = Patterns::class.java.getDeclaredField("EMAIL_ADDRESS")
    field.isAccessible = true

    val asignadoDirecto: Boolean = runCatching<Boolean> {
        field.set(null, patternValido)
        true
    }.getOrDefault(false)

    if (asignadoDirecto) return

    val asignadoConModifiers: Boolean = runCatching<Boolean> {
        val modifiersField: Field = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
        field.set(null, patternValido)
        true
    }.getOrDefault(false)

    if (asignadoConModifiers) return

    val unsafeClass: Class<*> = Class.forName("sun.misc.Unsafe")
    val unsafeField: Field = unsafeClass.getDeclaredField("theUnsafe")
    unsafeField.isAccessible = true
    val unsafe: Any = unsafeField.get(null)
    val staticFieldBase: Any = unsafeClass
        .getMethod("staticFieldBase", Field::class.java)
        .invoke(unsafe, field)
    val staticFieldOffset: Long = unsafeClass
        .getMethod("staticFieldOffset", Field::class.java)
        .invoke(unsafe, field) as Long

    unsafeClass
        .getMethod(
            "putObject",
            Any::class.java,
            Long::class.javaPrimitiveType,
            Any::class.java
        )
        .invoke(unsafe, staticFieldBase, staticFieldOffset, patternValido)
}

@OptIn(ExperimentalCoroutinesApi::class)
class ContactoViewModelTest : StringSpec({

    val dispatcher = UnconfinedTestDispatcher()
    var repository: ContactoRepository = mockk(relaxed = true)
    var contactosFlow: MutableStateFlow<List<ContactoEntity>> = MutableStateFlow(emptyList())

    beforeSpec {
        Dispatchers.setMain(dispatcher)
        mockkStatic(Log::class)
        every { Log.e(any<String>(), any<String>(), any<Throwable>()) } returns 0
        every { Log.e(any<String>(), any<String>()) } returns 0
        every { Log.w(any<String>(), any<String>(), any<Throwable>()) } returns 0
        every { Log.w(any<String>(), any<String>()) } returns 0
        every { Log.w(any<String>(), any<Throwable>()) } returns 0

        asegurarPatternsEmailAddress()
    }

    afterSpec {
        unmockkAll()
        Dispatchers.resetMain()
    }

    beforeEach {
        contactosFlow = MutableStateFlow(emptyList())
        repository = mockk(relaxed = true) {
            every { listaContactos } returns contactosFlow
            coEvery { sincronizarDesdeServidor() } returns Unit
            coEvery { guardarContacto(any()) } returns Unit
            coEvery { actualizarContacto(any()) } returns Unit
            coEvery { eliminarContacto(any()) } returns Unit
            every { obtenerPorId(any()) } returns null
        }
    }

    "debe filtrar telefono solo a digitos y maximo 9 caracteres y limpiar error" {
        val vm = ContactoViewModel(repository)
        vm.enviarFormulario()
        vm.telefonoError shouldBe "El teléfono es obligatorio *"

        vm.onTelefonoChanged("9a1b2c3d4e5f6g7h8i9j0")
        vm.telefono shouldBe "912345678"
        vm.telefonoError shouldBe null
    }

    "debe validar email invalido y telefono que no empieza con 9 o no tiene 9 digitos" {
        val vm = ContactoViewModel(repository)
        vm.onNombreChanged("Ana")
        vm.onEmailChanged("correo-no-valido")
        vm.onTelefonoChanged("812345678")
        vm.onAsuntoChanged("Quiero colaborar")
        vm.onMensajeChanged("Hola")

        vm.enviarFormulario()

        vm.emailError shouldBe "Ingresa un correo electrónico válido."
        vm.telefonoError shouldBe "Debe ingresar 9 dígitos comenzando con 9 (Ej: 912345678)."
        coVerify(exactly = 0) { repository.guardarContacto(any()) }
    }

    "debe enviar formulario valido, guardar contacto y marcar isSubmitted" {
        val vm = ContactoViewModel(repository)
        var exitoEjecutado = false

        runTest(dispatcher) {
            vm.onNombreChanged("Ana Pérez")
            vm.onEmailChanged("ana@correo.cl")
            vm.onTelefonoChanged("987654321")
            vm.onAsuntoChanged("Quiero colaborar")
            vm.onMensajeChanged("Consulta de prueba")

            vm.enviarFormulario { exitoEjecutado = true }
            advanceUntilIdle()
        }

        vm.isSubmitted.value shouldBe true
        exitoEjecutado shouldBe true
        coVerify(exactly = 1) {
            repository.guardarContacto(
                match {
                    it.nombre == "Ana Pérez" &&
                            it.email == "ana@correo.cl" &&
                            it.telefono == "987654321" &&
                            it.asunto == "Quiero colaborar" &&
                            it.mensaje == "Consulta de prueba"
                }
            )
        }
    }

    "debe manejar error al guardar contacto en enviarFormulario" {
        coEvery { repository.guardarContacto(any()) } throws RuntimeException("Error red")
        val vm = ContactoViewModel(repository)

        runTest(dispatcher) {
            vm.onNombreChanged("Ana")
            vm.onEmailChanged("ana@correo.cl")
            vm.onTelefonoChanged("987654321")
            vm.onAsuntoChanged("Quiero colaborar")
            vm.onMensajeChanged("Hola")

            vm.enviarFormulario()
            advanceUntilIdle()
        }

        vm.errorMessage.value shouldBe "No pudimos enviar tu mensaje en este momento. Intenta más tarde."
        vm.isLoading.value shouldBe false
    }

    "debe cambiar filtro, limpiar error y reiniciar formulario" {
        val vm = ContactoViewModel(repository)
        vm.cambiarFiltro("Pendiente")
        vm.filtroActual shouldBe "Pendiente"

        vm.onNombreChanged("Ana")
        vm.onEmailChanged("ana@correo.cl")
        vm.onTelefonoChanged("987654321")
        vm.onAsuntoChanged("Quiero colaborar")
        vm.onMensajeChanged("Mensaje")
        vm.limpiarError()
        vm.errorMessage.value shouldBe null

        vm.reiniciarFormulario()
        vm.nombre shouldBe ""
        vm.email shouldBe ""
        vm.telefono shouldBe ""
        vm.asunto shouldBe ""
        vm.mensaje shouldBe ""
        vm.isSubmitted.value shouldBe false
    }

    "debe buscar por id, actualizar estado y eliminar contacto" {
        val contacto = ContactoEntity(
            id = 7,
            nombre = "Luis",
            email = "luis@correo.cl",
            telefono = "911223344",
            asunto = "Ley de Inclusión Laboral",
            mensaje = "Consulta",
            estado = "Pendiente"
        )
        every { repository.obtenerPorId(7) } returns contacto
        val vm = ContactoViewModel(repository)

        vm.buscarPorId(7) shouldBe contacto

        runTest(dispatcher) {
            vm.cambiarEstadoContacto(contacto, "Contestado")
            vm.eliminarContacto(contacto)
            advanceUntilIdle()
        }

        coVerify { repository.actualizarContacto(contacto.copy(estado = "Contestado")) }
        coVerify { repository.eliminarContacto(contacto) }
    }
})