package com.example.lechendasapp.data.model

import java.util.Date

data class AuthToken (
    val id: Int = 1,
    val idToken: String,
    val accessToken: String,
    val tokenType: String,
    val refreshToken: String,
    val expiresAt: Date,
    val scope: String
)