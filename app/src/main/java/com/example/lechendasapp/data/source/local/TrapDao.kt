package com.example.lechendasapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TrapDao {
    @Query("SELECT * FROM trap")
    fun observeAll(): Flow<List<LocalTrap>>

    @Query("SELECT * FROM trap WHERE monitor_log_id = :monitorLogId")
    fun observeByMonitorLogId(monitorLogId: Long): Flow<List<LocalTrap>>

    @Query("SELECT * FROM trap WHERE id = :id")
    fun observeById(id: Long): Flow<LocalTrap>


    @Query("SELECT * FROM trap")
    suspend fun getAll(): List<LocalTrap>

    @Query("SELECT * FROM trap WHERE id = :id")
    suspend fun getById(id: Long): LocalTrap?


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trap: LocalTrap): Long

    @Update
    suspend fun update(trap: LocalTrap)

    @Delete
    suspend fun delete(trap: LocalTrap)

    @Query("DELETE FROM trap WHERE id = :id")
    suspend fun deleteById(id: Long)

}