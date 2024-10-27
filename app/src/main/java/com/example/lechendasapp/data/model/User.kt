package com.example.lechendasapp.data.model

data class User(
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val birthDate: String,
    val country: String,
    val occupation: String? = null,
)