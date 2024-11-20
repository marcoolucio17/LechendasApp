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

    @Query("SELECT * FROM vegetation WHERE monitor_log_id = :monitorLogId")
    fun observeByMonitorLogId(monitorLogId: Long): Flow<List<LocalVegetation>>

    @Query("SELECT * FROM vegetation WHERE id = :id")
    fun observeById(id: Long): Flow<LocalVegetation>

    @Query("SELECT * FROM vegetation")
    suspend fun getAll(): List<LocalVegetation>

    @Query("SELECT * FROM vegetation WHERE id = :id")
    suspend fun getById(id: Long): LocalVegetation?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vegetation: LocalVegetation): Long

    @Update
    suspend fun update(vegetation: LocalVegetation)

    @Delete
    suspend fun delete(vegetation: LocalVegetation)

    @Query("DELETE FROM vegetation WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT COUNT(*) FROM vegetation")
    suspend fun count(): Int

    @Query("DELETE FROM vegetation WHERE monitor_log_id = :monitorLogId")
    suspend fun deleteByMonitorLogId(monitorLogId: Long)

}