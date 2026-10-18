package com.example.appcorpaliv_grupo11.model

data class ContactoUiState(
    val nombre: String = "",
    val email: String = "",
    val asunto: String = "",
    val mensaje: String = "",
    val isSubmitted: Boolean = false
)