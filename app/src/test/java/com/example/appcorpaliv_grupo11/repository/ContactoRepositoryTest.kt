package com.example.appcorpaliv_grupo11.repository

import com.example.appcorpaliv_grupo11.data.ContactoDao
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class ContactoRepositoryTest : StringSpec({

    "debe emitir la lista de contactos desde el DAO" {
        runTest {
            val contacto1 = ContactoEntity(
                id = 1,
                nombre = "Ana Pérez",
                email = "ana@correo.cl",
                asunto = "Consulta",
                mensaje = "Hola, necesito información"
            )
            val contacto2 = ContactoEntity(
                id = 2,
                nombre = "Luis Soto",
                email = "luis@correo.cl",
                asunto = "Soporte",
                mensaje = "Tengo una duda"
            )
            val daoMock = mockk<ContactoDao>(relaxed = true)
            every { daoMock.obtenerTodosLosContactos() } returns flowOf(listOf(contacto1, contacto2))

            val repo = ContactoRepository(daoMock)
            val emitidos = repo.listaContactos.first()

            emitidos shouldContainExactly listOf(contacto1, contacto2)
        }
    }

    "debe llamar a guardarContacto en el DAO con la entidad correcta" {
        runTest {
            val nuevoContacto = ContactoEntity(
                nombre = "Camila Díaz",
                email = "camila@correo.cl",
                asunto = "Registro",
                mensaje = "Mensaje de prueba"
            )
            val daoMock = mockk<ContactoDao>(relaxed = true)
            every { daoMock.obtenerTodosLosContactos() } returns emptyFlow()
            coEvery { daoMock.guardarContacto(any()) } returns Unit

            val repo = ContactoRepository(daoMock)
            repo.guardarContacto(nuevoContacto)

            coVerify(exactly = 1) { daoMock.guardarContacto(nuevoContacto) }
        }
    }

    "debe retornar el contacto buscado por id" {
        runTest {
            val esperado = ContactoEntity(
                id = 5,
                nombre = "Diego Ruiz",
                email = "diego@correo.cl",
                asunto = "Seguimiento",
                mensaje = "Confirmación"
            )
            val daoMock = mockk<ContactoDao>(relaxed = true)
            every { daoMock.obtenerTodosLosContactos() } returns emptyFlow()
            coEvery { daoMock.obtenerContactoPorId(5) } returns esperado

            val repo = ContactoRepository(daoMock)
            val obtenido = repo.obtenerPorId(5)

            obtenido shouldBe esperado
        }
    }
})