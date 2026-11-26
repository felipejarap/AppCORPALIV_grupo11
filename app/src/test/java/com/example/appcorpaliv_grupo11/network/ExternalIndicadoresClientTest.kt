package com.example.appcorpaliv_grupo11.network

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class ExternalIndicadoresClientTest : StringSpec({

    "debe inicializar la instancia de Retrofit y exponer la URL de respaldo" {
        ExternalIndicadoresClient.FALLBACK_URL shouldBe "https://findic.cl/api"
        ExternalIndicadoresClient.api.shouldNotBeNull()
    }
})