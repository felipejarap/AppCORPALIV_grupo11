package com.example.appcorpaliv_grupo11.repository

import android.util.Log
import com.example.appcorpaliv_grupo11.model.ContactoEntity
import com.example.appcorpaliv_grupo11.network.ContactoApiService
import com.example.appcorpaliv_grupo11.network.ContactoDto
import com.example.appcorpaliv_grupo11.network.RetrofitClient
import com.example.appcorpaliv_grupo11.network.toDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class ContactoRepositoryTest : StringSpec({

    lateinit var apiMock: ContactoApiService

    beforeTest {
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0

        apiMock = mockk(relaxed = true)
        mockkObject(RetrofitClient)
        every { RetrofitClient.apiService } returns apiMock
    }

    afterTest {
        unmockkAll()
    }

    "debe sincronizar y emitir la lista ordenada por id descendente" {
        runTest {
            val c1 = ContactoEntity(id = 1, nombre = "Ana", email = "ana@correo.cl", asunto = "Consulta", mensaje = "Hola")
            val c2 = ContactoEntity(id = 2, nombre = "Luis", email = "luis@correo.cl", asunto = "Soporte", mensaje = "Duda")
            coEvery { apiMock.obtenerTodos() } returns Response.success(
                listOf(c1.toDto(incluirId = true), c2.toDto(incluirId = true))
            )

            val repo = ContactoRepository()
            repo.sincronizarDesdeServidor()

            repo.listaContactos.first() shouldContainExactly listOf(c2, c1)
        }
    }

    "debe lanzar error sanitizado si falla sincronizarDesdeServidor" {
        runTest {
            coEvery { apiMock.obtenerTodos() } returns Response.error<List<ContactoDto>>(500, "Error".toResponseBody())

            val repo = ContactoRepository()
            val ex = shouldThrow<Exception> { repo.sincronizarDesdeServidor() }

            ex.message shouldBe "No se pudo obtener la información solicitada."
        }
    }

    "debe guardar un contacto y agregarlo al inicio de la lista" {
        runTest {
            val nuevo = ContactoEntity(id = 0, nombre = "Camila", email = "camila@correo.cl", asunto = "Registro", mensaje = "Prueba")
            val creado = nuevo.copy(id = 10)
            coEvery { apiMock.crearContacto(nuevo.toDto(incluirId = false)) } returns Response.success(creado.toDto(incluirId = true))

            val repo = ContactoRepository()
            repo.guardarContacto(nuevo)

            coVerify(exactly = 1) { apiMock.crearContacto(nuevo.toDto(incluirId = false)) }
            repo.listaContactos.first() shouldContainExactly listOf(creado)
        }
    }

    "debe lanzar error sanitizado si falla guardarContacto" {
        runTest {
            val nuevo = ContactoEntity(nombre = "Camila", email = "camila@correo.cl", asunto = "Registro", mensaje = "Prueba")
            coEvery { apiMock.crearContacto(any()) } returns Response.error<ContactoDto>(400, "Bad Request".toResponseBody())

            val repo = ContactoRepository()
            val ex = shouldThrow<Exception> { repo.guardarContacto(nuevo) }

            ex.message shouldBe "No se pudo registrar la información."
        }
    }

    "debe actualizar un contacto en el servidor y refrescarlo en memoria" {
        runTest {
            val original = ContactoEntity(id = 3, nombre = "Pedro", email = "pedro@correo.cl", asunto = "Consulta", mensaje = "Msg", estado = "Pendiente")
            val editado = original.copy(estado = "Contestado")

            coEvery { apiMock.obtenerTodos() } returns Response.success(listOf(original.toDto(incluirId = true)))
            coEvery { apiMock.actualizarContacto(3, editado.toDto(incluirId = true)) } returns Response.success(editado.toDto(incluirId = true))

            val repo = ContactoRepository()
            repo.sincronizarDesdeServidor()
            repo.actualizarContacto(editado)

            repo.listaContactos.first() shouldContainExactly listOf(editado)
        }
    }

    "debe lanzar error sanitizado si falla actualizarContacto" {
        runTest {
            val editado = ContactoEntity(id = 3, nombre = "Pedro", email = "pedro@correo.cl", asunto = "Consulta", mensaje = "Msg")
            coEvery { apiMock.actualizarContacto(any(), any()) } returns Response.error<ContactoDto>(500, "Error".toResponseBody())

            val repo = ContactoRepository()
            val ex = shouldThrow<Exception> { repo.actualizarContacto(editado) }

            ex.message shouldBe "No se pudo actualizar la información."
        }
    }

    "debe eliminar un contacto en el servidor y removerlo de la lista" {
        runTest {
            val c1 = ContactoEntity(id = 7, nombre = "Laura", email = "laura@correo.cl", asunto = "Borrar", mensaje = "Texto")
            coEvery { apiMock.obtenerTodos() } returns Response.success(listOf(c1.toDto(incluirId = true)))
            coEvery { apiMock.eliminarContacto(7) } returns Response.success(Unit)

            val repo = ContactoRepository()
            repo.sincronizarDesdeServidor()
            repo.eliminarContacto(c1)

            repo.listaContactos.first().shouldBeEmpty()
        }
    }

    "debe lanzar error sanitizado si falla eliminarContacto" {
        runTest {
            val c1 = ContactoEntity(id = 7, nombre = "Laura", email = "laura@correo.cl", asunto = "Borrar", mensaje = "Texto")
            coEvery { apiMock.eliminarContacto(7) } returns Response.error<Unit>(404, "Not Found".toResponseBody())

            val repo = ContactoRepository()
            val ex = shouldThrow<Exception> { repo.eliminarContacto(c1) }

            ex.message shouldBe "No se pudo eliminar el registro."
        }
    }

    "debe retornar el contacto buscado por id o null si no existe" {
        runTest {
            val esperado = ContactoEntity(id = 5, nombre = "Diego", email = "diego@correo.cl", asunto = "Seguimiento", mensaje = "Ok")
            coEvery { apiMock.obtenerTodos() } returns Response.success(listOf(esperado.toDto(incluirId = true)))

            val repo = ContactoRepository()
            repo.sincronizarDesdeServidor()

            repo.obtenerPorId(5) shouldBe esperado
            repo.obtenerPorId(999) shouldBe null
        }
    }
})