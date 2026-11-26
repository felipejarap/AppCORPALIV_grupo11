package com.example.appcorpaliv_grupo11.viewModel

import android.app.Application
import com.example.appcorpaliv_grupo11.data.EstadoDataStore
import com.example.appcorpaliv_grupo11.navigation.NavigationEvent
import com.example.appcorpaliv_grupo11.navigation.Screen
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest : StringSpec({

    val dispatcher = StandardTestDispatcher()
    var application: Application = mockk(relaxed = true)
    var darkModeFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    beforeSpec {
        Dispatchers.setMain(dispatcher)
    }

    afterSpec {
        unmockkAll()
        Dispatchers.resetMain()
    }

    beforeEach {
        unmockkAll()
        Dispatchers.setMain(dispatcher)
        application = mockk(relaxed = true)
        darkModeFlow = MutableStateFlow(false)

        mockkConstructor(EstadoDataStore::class)
        every { anyConstructed<EstadoDataStore>().isDarkMode } returns darkModeFlow
        coEvery { anyConstructed<EstadoDataStore>().saveDarkModeState(any()) } returns Unit
    }

    "debe iniciar con isDarkTheme en false por defecto" {
        val vm = MainViewModel(application)
        vm.isDarkTheme.value shouldBe false
    }

    "debe alternar y guardar el estado del tema oscuro en DataStore" {
        val vm = MainViewModel(application)

        runTest(dispatcher) {
            vm.toggleTheme()
            advanceUntilIdle()
        }

        coVerify(exactly = 1) { anyConstructed<EstadoDataStore>().saveDarkModeState(true) }
    }

    "debe emitir evento NavigateTo al llamar navigateTo" {
        val vm = MainViewModel(application)
        val destino = Screen.Contacto

        runTest(dispatcher) {
            val emitido = async { vm.navigationEvents.first() }
            advanceUntilIdle()
            vm.navigateTo(destino)
            advanceUntilIdle()

            emitido.await() shouldBe NavigationEvent.NavigateTo(route = destino)
        }
    }

    "debe emitir evento PopBackStack y NavigateUp" {
        val vm = MainViewModel(application)

        runTest(dispatcher) {
            val backEvent = async { vm.navigationEvents.first() }
            advanceUntilIdle()
            vm.navigateBack()
            advanceUntilIdle()
            backEvent.await() shouldBe NavigationEvent.PopBackStack

            val upEvent = async { vm.navigationEvents.first() }
            advanceUntilIdle()
            vm.navigateUp()
            advanceUntilIdle()
            upEvent.await() shouldBe NavigationEvent.NavigateUp
        }
    }
})