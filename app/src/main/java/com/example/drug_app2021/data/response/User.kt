package com.example.drug_app2021.data.response

data class User(
    val id: Int,
    val cellphone: String,
    val email: String,
    val name: String,
    val status: String,
    val token: String
)