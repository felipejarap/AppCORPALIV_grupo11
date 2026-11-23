package com.example.appcorpaliv_grupo11.network

import retrofit2.Response
import retrofit2.http.*

interface ContactoApiService {

    @GET("api/contactos")
    suspend fun obtenerTodos(): Response<List<ContactoDto>>

    @POST("api/contactos")
    suspend fun crearContacto(@Body contacto: ContactoDto): Response<ContactoDto>

    @PUT("api/contactos/{id}")
    suspend fun actualizarContacto(
        @Path("id") id: Int,
        @Body contacto: ContactoDto
    ): Response<ContactoDto>

    @DELETE("api/contactos/{id}")
    suspend fun eliminarContacto(@Path("id") id: Int): Response<Unit>
}