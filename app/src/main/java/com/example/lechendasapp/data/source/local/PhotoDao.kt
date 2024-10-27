package com.example.lechendasapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    //Basic methods

    @Query("SELECT * FROM photos")
    fun observeAll(): Flow<List<LocalPhoto>>

    @Query("SELECT * FROM photos WHERE id = :id")
    fun observeById(id: String): Flow<LocalPhoto>

    @Query("SELECT * FROM photos")
    suspend fun getAll(): List<LocalPhoto>

    @Query("SELECT * FROM photos WHERE id = :id")
    suspend fun getById(id: String): LocalPhoto?

    @Insert
    suspend fun insert(photo: LocalPhoto)

    @Update
    suspend fun update(photo: LocalPhoto)

    @Delete
    suspend fun delete(photo: LocalPhoto)
}