package com.example.lechendasapp.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "auth_tokens")
data class LocalAuthToken(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "id_token") val idToken: String,
    @ColumnInfo(name = "access_token") val accessToken: String,
    @ColumnInfo(name = "token_type") val tokenType: String,
    @ColumnInfo(name = "refresh_token") val refreshToken: String,
    @ColumnInfo(name = "expires_at") val expiresAt: Date,
    val scope: String
)