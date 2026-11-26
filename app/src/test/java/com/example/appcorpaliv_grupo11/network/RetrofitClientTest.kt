package com.example.appcorpaliv_grupo11.network

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull

class RetrofitClientTest : StringSpec({

    "debe inicializar correctamente la instancia de ContactoApiService" {
        RetrofitClient.apiService.shouldNotBeNull()
    }
})