package com.example.lechendasapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthTokenDao {
    @Query("SELECT * FROM auth_tokens WHERE id = 1")
    fun getAuthToken(): Flow<LocalAuthToken?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAuthToken(token: LocalAuthToken)

    @Query("DELETE FROM auth_tokens")
    suspend fun clearAuthToken()
}