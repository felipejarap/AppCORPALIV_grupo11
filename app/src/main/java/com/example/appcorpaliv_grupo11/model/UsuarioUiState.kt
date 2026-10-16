package com.example.appcorpaliv_grupo11.model

data class UsuarioUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val errores: UsuarioErrores = UsuarioErrores(),
    val aceptaTerminos: Boolean = false
)