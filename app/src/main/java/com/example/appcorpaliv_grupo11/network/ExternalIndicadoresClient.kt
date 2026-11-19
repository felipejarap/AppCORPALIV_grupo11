package com.example.appcorpaliv_grupo11.network

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

interface ExternalIndicadoresApi {
    @GET("api")
    suspend fun obtenerIndicadoresDiarios(): Response<IndicadoresResponse>

    @GET
    suspend fun obtenerIndicadoresDesdeUrl(@Url url: String): Response<IndicadoresResponse>
}

object ExternalIndicadoresClient {
    private const val PRIMARY_BASE_URL = "https://mindicador.cl/"
    const val FALLBACK_URL = "https://findic.cl/api"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    val api: ExternalIndicadoresApi by lazy {
        Retrofit.Builder()
            .baseUrl(PRIMARY_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExternalIndicadoresApi::class.java)
    }
}