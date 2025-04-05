package com.teste1contactos.data.model

data class Usuario(
    val id: String = "",
    val nome: String = "",
    val email: String = "",
    val foto: String = "",
    val observacoes: String = "",
    val tipo: String = "user", // "adm" ou "user"
    val idEmpresa: String = "", // Empresa principal vinculada
    val empresasPermitidas: List<String> = listOf() // IDs das empresas permitidas
) 