package com.example.lechendasapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VegetationDao {
    @Query("SELECT * FROM vegetation")
    fun observeAll(): Flow<List<LocalVegetation>>

    @Query("SELECT * FROM vegetation WHERE id = :id")
    fun observeById(id: Long): Flow<LocalVegetation>

    @Query("SELECT * FROM vegetation")
    suspend fun getAll(): List<LocalVegetation>

    @Query("SELECT * FROM vegetation WHERE id = :id")
    suspend fun getById(id: Long): LocalVegetation?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vegetation: LocalVegetation)

    @Update
    suspend fun update(vegetation: LocalVegetation)

    @Delete
    suspend fun delete(vegetation: LocalVegetation)


}