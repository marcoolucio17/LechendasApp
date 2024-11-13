package com.example.lechendasapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDao {
    @Query("SELECT * FROM animals")
    fun observeAll(): Flow<List<LocalAnimal>>

    @Query("SELECT * FROM animals WHERE monitor_log_id = :monitorLogId")
    fun observeByMonitorLogId(monitorLogId: Long): Flow<List<LocalAnimal>>

    @Query("SELECT * FROM animals WHERE id = :id")
    fun observeById(id: Long): Flow<LocalAnimal>

    @Query("SELECT * FROM animals")
    suspend fun getAll(): List<LocalAnimal>

    @Query("SELECT * FROM animals WHERE id = :id")
    suspend fun getById(id: Long): LocalAnimal?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(animal: LocalAnimal)

    @Update
    suspend fun update(animal: LocalAnimal)

    @Delete
    suspend fun delete(animal: LocalAnimal)

    @Query("DELETE FROM animals WHERE id = :id")
    suspend fun deleteById(id: Long)
}