package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.User
import com.example.lechendasapp.data.source.local.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultUserRepository @Inject constructor(
    private val localDataSource: UserDao,
) : UserRepository {
    override fun getUsersStream(): Flow<List<User>> {
        return localDataSource.observeAll().map { users ->
                users.toExternal()
        }
    }

    override fun getIndividualUserStream(userId: Long): Flow<User> {
        return localDataSource.observeById(userId).map { it.toExternal() }
    }

    override suspend fun getAllUsers(): List<User> {
        return localDataSource.getAll().toExternal()
    }

    override suspend fun getUserById(userId: Long): User? {
        return localDataSource.getById(userId)?.toExternal()
    }

    override suspend fun getUserByEmail(email: String): User? {
        return localDataSource.getByEmail(email)?.toExternal()
    }
    

    override suspend fun insertUser(user: User) {
        localDataSource.insert(user.toLocal())
    }

    override suspend fun deleteUser(user: User) {
        localDataSource.delete(user.toLocal())
    }

}