package com.example.appcorpaliv_grupo11.network

import com.google.gson.annotations.SerializedName

/**
 * Modelo de respuesta de la API externa https://mindicador.cl/api
 */
data class IndicadoresResponse(
    @SerializedName("fecha")
    val fecha: String = "",

    @SerializedName("uf")
    val uf: IndicadorDetalle? = null,

    @SerializedName("utm")
    val utm: IndicadorDetalle? = null,

    @SerializedName("dolar")
    val dolar: IndicadorDetalle? = null
)

data class IndicadorDetalle(
    @SerializedName("codigo")
    val codigo: String = "",

    @SerializedName("nombre")
    val nombre: String = "",

    @SerializedName("unidad_medida")
    val unidadMedida: String = "",

    @SerializedName("fecha")
    val fecha: String = "",

    @SerializedName("valor")
    val valor: Double = 0.0
)