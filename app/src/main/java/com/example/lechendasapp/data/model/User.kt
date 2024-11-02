package com.example.lechendasapp.data.model

data class User(
    val id: Long,

    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val country: String,
    val occupation: String? = null,
    val height: String,

    val email: String,
    val password: String,

    val team: Int,
    val role: String,
)