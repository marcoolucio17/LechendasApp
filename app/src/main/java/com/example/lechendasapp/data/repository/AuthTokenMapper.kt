package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.AuthToken
import com.example.lechendasapp.data.source.local.AuthTokenDao
import com.example.lechendasapp.data.source.local.LocalAuthToken

fun AuthToken.toLocalAuthToken() = LocalAuthToken(
    id = id,
    idToken = idToken,
    accessToken = accessToken,
    tokenType = tokenType,
    refreshToken = refreshToken,
    expiresAt = expiresAt,
    scope = scope
)

fun List<AuthToken>.toLocalAuthToken() = map { it.toLocalAuthToken() }

fun LocalAuthToken.toAuthToken() = AuthToken (
    idToken = idToken,
    accessToken = accessToken,
    tokenType = tokenType,
    refreshToken = refreshToken,
    expiresAt = expiresAt,
    scope = scope
)

@JvmName("localToExternalAuthToken")
fun List<LocalAuthToken>.toAuthToken() = map { it.toAuthToken() }