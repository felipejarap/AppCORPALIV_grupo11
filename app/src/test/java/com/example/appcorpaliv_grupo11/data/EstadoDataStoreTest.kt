package com.example.appcorpaliv_grupo11.data

import android.content.Context
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import java.io.File
import java.nio.file.Files

class EstadoDataStoreTest : StringSpec({

    lateinit var tempDir: File
    lateinit var estadoDataStore: EstadoDataStore

    beforeSpec {
        tempDir = Files.createTempDirectory("corpaliv_datastore_test").toFile()
        val contextMock = mockk<Context>(relaxed = true)
        every { contextMock.applicationContext } returns contextMock
        every { contextMock.filesDir } returns tempDir

        estadoDataStore = EstadoDataStore(contextMock)
    }

    afterSpec {
        tempDir.deleteRecursively()
    }

    "debe devolver false por defecto cuando no existe preferencia guardada" {
        runTest {
            estadoDataStore.isDarkMode.first() shouldBe false
        }
    }

    "debe guardar y emitir true al activar el modo oscuro y luego volver a false" {
        runTest {
            estadoDataStore.saveDarkModeState(true)
            estadoDataStore.isDarkMode.first() shouldBe true

            estadoDataStore.saveDarkModeState(false)
            estadoDataStore.isDarkMode.first() shouldBe false
        }
    }
})