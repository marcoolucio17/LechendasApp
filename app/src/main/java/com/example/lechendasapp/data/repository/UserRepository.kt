package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsersStream(): Flow<List<User>>

    fun getIndividualUserStream(userId: Long): Flow<User>

    suspend fun getAllUsers(): List<User>

    suspend fun getUserById(userId: Long): User?

    suspend fun getUserByEmail(email: String): User?

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)

}