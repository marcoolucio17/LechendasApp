package com.example.lechendasapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CoverageDao {
    @Query("SELECT * FROM coverage")
    fun observeAll(): Flow<List<LocalCoverage>>

    @Query("SELECT * FROM coverage WHERE monitor_log_id = :monitorLogId")
    fun observeByMonitorLogId(monitorLogId: Long): Flow<List<LocalCoverage>>

    @Query("SELECT * FROM coverage WHERE id = :id")
    fun observeById(id: Long): Flow<LocalCoverage>

    @Query("SELECT * FROM coverage")
    suspend fun getAll(): List<LocalCoverage>

    @Query("SELECT * FROM coverage WHERE id = :id")
    suspend fun getById(id: Long): LocalCoverage?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(coverage: LocalCoverage)

    @Update
    suspend fun update(coverage: LocalCoverage)

    @Delete
    suspend fun delete(coverage: LocalCoverage)

    @Query("DELETE FROM coverage WHERE id = :id")
    suspend fun deleteById(id: Long)

}