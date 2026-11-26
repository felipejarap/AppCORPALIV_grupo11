package com.example.appcorpaliv_grupo11.network

import com.google.gson.Gson
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class IndicadoresResponseTest : StringSpec({

    "debe inicializar IndicadoresResponse e IndicadorDetalle con valores por defecto" {
        val response = IndicadoresResponse()
        val detalle = IndicadorDetalle()

        response.fecha shouldBe ""
        response.uf shouldBe null
        response.utm shouldBe null
        response.dolar shouldBe null

        detalle.codigo shouldBe ""
        detalle.nombre shouldBe ""
        detalle.unidadMedida shouldBe ""
        detalle.fecha shouldBe ""
        detalle.valor shouldBe 0.0
    }

    "debe deserializar correctamente el JSON de mindicador.cl usando Gson" {
        val json = """
            {
              "fecha": "2026-11-26T00:00:00.000Z",
              "uf": {
                "codigo": "uf",
                "nombre": "Unidad de fomento (UF)",
                "unidad_medida": "Pesos",
                "fecha": "2026-11-26T00:00:00.000Z",
                "valor": 38500.5
              },
              "utm": {
                "codigo": "utm",
                "nombre": "Unidad Tributaria Mensual (UTM)",
                "unidad_medida": "Pesos",
                "fecha": "2026-11-01T00:00:00.000Z",
                "valor": 66628.0
              },
              "dolar": {
                "codigo": "dolar",
                "nombre": "Dólar observado",
                "unidad_medida": "Pesos",
                "fecha": "2026-11-26T00:00:00.000Z",
                "valor": 950.25
              }
            }
        """.trimIndent()

        val resultado = Gson().fromJson(json, IndicadoresResponse::class.java)

        resultado.fecha shouldBe "2026-11-26T00:00:00.000Z"
        resultado.uf?.codigo shouldBe "uf"
        resultado.uf?.nombre shouldBe "Unidad de fomento (UF)"
        resultado.uf?.unidadMedida shouldBe "Pesos"
        resultado.uf?.fecha shouldBe "2026-11-26T00:00:00.000Z"
        resultado.uf?.valor shouldBe 38500.5
        resultado.utm?.valor shouldBe 66628.0
        resultado.dolar?.valor shouldBe 950.25
    }
})