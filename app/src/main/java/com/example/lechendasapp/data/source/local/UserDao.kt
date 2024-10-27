package com.example.lechendasapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    //Basic methods

    @Query("SELECT * FROM users")
    fun observeAll(): Flow<List<LocalUser>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun observeById(id: Long): Flow<LocalUser>

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<LocalUser>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getById(id: Long): LocalUser?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: LocalUser)

    @Update
    suspend fun update(user: LocalUser)

    @Delete
    suspend fun delete(user: LocalUser)
}