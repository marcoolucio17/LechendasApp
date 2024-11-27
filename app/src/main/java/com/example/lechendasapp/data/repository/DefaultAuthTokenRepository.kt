package com.example.lechendasapp.data.repository
import com.example.lechendasapp.data.model.AuthToken
import com.example.lechendasapp.data.source.local.AuthTokenDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAuthTokenRepository @Inject constructor(
    private val localDataSource: AuthTokenDao
) : AuthTokenRepository {
    override fun getAuthToken(): Flow<AuthToken?> {
        return localDataSource.getAuthToken().map { it?.toAuthToken() }
    }

    override suspend fun saveAuthToken(token: AuthToken) {
        localDataSource.saveAuthToken(token.toLocalAuthToken())
    }

    override suspend fun getIdToken(): String? {
        return localDataSource.getIdToken()
    }

    override suspend fun clearAuthToken() {
        localDataSource.clearAuthToken()
    }
}