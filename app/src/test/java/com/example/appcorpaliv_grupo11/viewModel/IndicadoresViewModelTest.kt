package com.example.appcorpaliv_grupo11.viewModel

import android.util.Log
import com.example.appcorpaliv_grupo11.network.ExternalIndicadoresClient
import com.example.appcorpaliv_grupo11.network.IndicadoresResponse
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class IndicadoresViewModelTest : StringSpec({

    val dispatcher = StandardTestDispatcher()

    beforeSpec {
        Dispatchers.setMain(dispatcher)
    }

    afterSpec {
        Dispatchers.resetMain()
    }

    beforeTest {
        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } returns 0
        every { Log.w(any<String>(), any<String>()) } returns 0

        mockkObject(ExternalIndicadoresClient)
        every { ExternalIndicadoresClient.api } returns mockk(relaxed = true)
    }

    afterTest {
        unmockkAll()
    }

    "debe cargar indicadores desde la API principal mindicador.cl sin llamar al respaldo" {
        runTest(dispatcher) {
            val respuestaEsperada = mockk<IndicadoresResponse>(relaxed = true)
            val apiMock = ExternalIndicadoresClient.api

            coEvery {
                apiMock.obtenerIndicadoresDiarios()
            } returns Response.success(respuestaEsperada)

            val viewModel = IndicadoresViewModel()
            advanceUntilIdle()

            viewModel.indicadores.value shouldBe respuestaEsperada
            viewModel.errorMessage.value shouldBe null
            viewModel.isLoading.value shouldBe false
            coVerify(exactly = 0) {
                apiMock.obtenerIndicadoresDesdeUrl(any())
            }
        }
    }

    "debe usar el respaldo findic.cl si falla la API principal" {
        runTest(dispatcher) {
            val respuestaRespaldo = mockk<IndicadoresResponse>(relaxed = true)
            val apiMock = ExternalIndicadoresClient.api

            coEvery {
                apiMock.obtenerIndicadoresDiarios()
            } returns Response.error<IndicadoresResponse>(503, "Service Unavailable".toResponseBody())

            coEvery {
                apiMock.obtenerIndicadoresDesdeUrl(ExternalIndicadoresClient.FALLBACK_URL)
            } returns Response.success(respuestaRespaldo)

            val viewModel = IndicadoresViewModel()
            advanceUntilIdle()

            viewModel.indicadores.value shouldBe respuestaRespaldo
            viewModel.errorMessage.value shouldBe null
            viewModel.isLoading.value shouldBe false
        }
    }

    "debe mostrar error sanitizado si fallan tanto la API principal como el respaldo" {
        runTest(dispatcher) {
            val apiMock = ExternalIndicadoresClient.api

            coEvery {
                apiMock.obtenerIndicadoresDiarios()
            } throws Exception("Timeout")

            coEvery {
                apiMock.obtenerIndicadoresDesdeUrl(ExternalIndicadoresClient.FALLBACK_URL)
            } returns Response.error<IndicadoresResponse>(500, "Server Error".toResponseBody())

            val viewModel = IndicadoresViewModel()
            advanceUntilIdle()

            viewModel.indicadores.value shouldBe null
            viewModel.errorMessage.value shouldBe "Información económica no disponible en este momento."
            viewModel.isLoading.value shouldBe false
        }
    }

    "debe mostrar error de conexion si el respaldo lanza una excepcion de red" {
        runTest(dispatcher) {
            val apiMock = ExternalIndicadoresClient.api

            coEvery {
                apiMock.obtenerIndicadoresDiarios()
            } throws Exception("Sin red")

            coEvery {
                apiMock.obtenerIndicadoresDesdeUrl(ExternalIndicadoresClient.FALLBACK_URL)
            } throws Exception("Sin red")

            val viewModel = IndicadoresViewModel()
            advanceUntilIdle()

            viewModel.indicadores.value shouldBe null
            viewModel.errorMessage.value shouldBe "No fue posible actualizar los indicadores. Revisa tu conexión."
            viewModel.isLoading.value shouldBe false
        }
    }
})