package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsersStream(): Flow<List<User>>

    fun getIndividualUserStream(userId: Long): Flow<User>

    suspend fun getAllUsers(): List<User>

    suspend fun getUserById(userId: Long): User?

    suspend fun addUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        birthdate: String,
        country: String,
        occupation: String?,
    )

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)

}