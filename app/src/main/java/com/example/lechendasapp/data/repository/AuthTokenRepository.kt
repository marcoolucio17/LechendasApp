package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface AuthTokenRepository {
    fun getAuthToken(): Flow<AuthToken?>
    suspend fun saveAuthToken(token: AuthToken)
    suspend fun getIdToken(): String?
    suspend fun clearAuthToken()
}