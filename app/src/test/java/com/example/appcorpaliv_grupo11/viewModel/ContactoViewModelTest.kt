package com.example.appcorpaliv_grupo11.viewModel

import com.example.appcorpaliv_grupo11.data.ContactoDao
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import com.example.appcorpaliv_grupo11.repository.ContactoRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class ContactoViewModelTest : StringSpec({

    val dispatcher = StandardTestDispatcher()

    beforeSpec {
        Dispatchers.setMain(dispatcher)
    }

    afterSpec {
        Dispatchers.resetMain()
    }

    fun crearViewModel(daoMock: ContactoDao = mockk(relaxed = true)): ContactoViewModel {
        every { daoMock.obtenerTodosLosContactos() } returns emptyFlow()
        val repository = ContactoRepository(daoMock)
        return ContactoViewModel(repository)
    }

    "debe inicializar campos vacios, filtro en Todos y sin errores ni envio" {
        runTest(dispatcher) {
            val viewModel = crearViewModel()

            viewModel.nombre shouldBe ""
            viewModel.email shouldBe ""
            viewModel.asunto shouldBe ""
            viewModel.mensaje shouldBe ""
            viewModel.filtroActual shouldBe "Todos"
            viewModel.isSubmitted.value shouldBe false
            viewModel.errorMessage.value shouldBe null
        }
    }

    "debe actualizar campos del formulario y limpiar error previo" {
        runTest(dispatcher) {
            val viewModel = crearViewModel()

            viewModel.onNombreChanged("Valentina Rojas")
            viewModel.onEmailChanged("valentina@correo.cl")
            viewModel.onAsuntoChanged("Consulta")
            viewModel.onMensajeChanged("Necesito informacion")

            viewModel.nombre shouldBe "Valentina Rojas"
            viewModel.email shouldBe "valentina@correo.cl"
            viewModel.asunto shouldBe "Consulta"
            viewModel.mensaje shouldBe "Necesito informacion"
            viewModel.errorMessage.value shouldBe null
        }
    }

    "debe cambiar el filtro activo" {
        runTest(dispatcher) {
            val viewModel = crearViewModel()

            viewModel.cambiarFiltro("Pendientes")
            viewModel.filtroActual shouldBe "Pendientes"

            viewModel.cambiarFiltro("Contestados")
            viewModel.filtroActual shouldBe "Contestados"
        }
    }

    "debe mostrar error si faltan campos obligatorios al enviar formulario" {
        runTest(dispatcher) {
            val daoMock = mockk<ContactoDao>(relaxed = true)
            val viewModel = crearViewModel(daoMock)

            viewModel.onNombreChanged("Carlos")
            viewModel.enviarFormulario()
            advanceUntilIdle()

            viewModel.errorMessage.value shouldBe "Por favor completa los campos obligatorios (*)."
            viewModel.isSubmitted.value shouldBe false
            coVerify(exactly = 0) { daoMock.guardarContacto(any()) }
        }
    }

    "debe cambiar el estado de un contacto llamando a actualizarContacto en el DAO" {
        runTest(dispatcher) {
            val daoMock = mockk<ContactoDao>(relaxed = true)
            val viewModel = crearViewModel(daoMock)
            val contacto = ContactoEntity(
                id = 3,
                nombre = "Pedro",
                email = "pedro@correo.cl",
                asunto = "Estado",
                mensaje = "Revisar",
                estado = "Pendiente"
            )

            viewModel.cambiarEstadoContacto(contacto, "Contestado")
            advanceUntilIdle()

            coVerify(exactly = 1) {
                daoMock.actualizarContacto(
                    match { it.id == 3 && it.estado == "Contestado" }
                )
            }
        }
    }

    "debe actualizar directamente un contacto llamando a actualizarContacto en el DAO" {
        runTest(dispatcher) {
            val daoMock = mockk<ContactoDao>(relaxed = true)
            val viewModel = crearViewModel(daoMock)
            val contactoActualizado = ContactoEntity(
                id = 4,
                nombre = "Mateo",
                email = "mateo@correo.cl",
                asunto = "Actualizacion",
                mensaje = "Texto editado",
                estado = "Contestado"
            )

            viewModel.actualizarContacto(contactoActualizado)
            advanceUntilIdle()

            coVerify(exactly = 1) { daoMock.actualizarContacto(contactoActualizado) }
        }
    }

    "debe eliminar un contacto y reiniciar el formulario correctamente" {
        runTest(dispatcher) {
            val daoMock = mockk<ContactoDao>(relaxed = true)
            val viewModel = crearViewModel(daoMock)
            val contacto = ContactoEntity(
                id = 7,
                nombre = "Laura",
                email = "laura@correo.cl",
                asunto = "Borrar",
                mensaje = "Texto"
            )

            viewModel.onNombreChanged("Laura")
            viewModel.onEmailChanged("laura@correo.cl")
            viewModel.onMensajeChanged("Texto")
            viewModel.eliminarContacto(contacto)
            viewModel.reiniciarFormulario()
            advanceUntilIdle()

            coVerify(exactly = 1) { daoMock.eliminarContacto(contacto) }
            viewModel.nombre shouldBe ""
            viewModel.email shouldBe ""
            viewModel.asunto shouldBe ""
            viewModel.mensaje shouldBe ""
            viewModel.isSubmitted.value shouldBe false
            viewModel.errorMessage.value shouldBe null
        }
    }
})