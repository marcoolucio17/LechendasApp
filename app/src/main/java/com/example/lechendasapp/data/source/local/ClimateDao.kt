package com.example.lechendasapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ClimateDao {
    @Query("SELECT * FROM climate")
    fun observeAll(): Flow<List<LocalClimate>>

    @Query("SELECT * FROM climate WHERE id = :id")
    fun observeById(id: Long): Flow<LocalClimate>

    @Query("SELECT * FROM climate")
    suspend fun getAll(): List<LocalClimate>

    @Query("SELECT * FROM climate WHERE id = :id")
    suspend fun getById(id: Long): LocalClimate?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(climate: LocalClimate)

    @Update
    suspend fun update(climate: LocalClimate)

    @Delete
    suspend fun delete(climate: LocalClimate)
}