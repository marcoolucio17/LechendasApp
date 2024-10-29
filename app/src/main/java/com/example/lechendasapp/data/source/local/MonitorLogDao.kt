package com.example.lechendasapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MonitorLogDao {

    //Basic methods

    @Query("SELECT * FROM monitor_logs")
    fun observeAll(): Flow<List<LocalMonitorLog>>

    @Query("SELECT * FROM monitor_logs WHERE id = :id")
    fun observeById(id: Long): Flow<LocalMonitorLog>

    @Query("SELECT * FROM monitor_logs")
    suspend fun getAll(): List<LocalMonitorLog>

    @Query("SELECT * FROM monitor_logs WHERE id = :id")
    suspend fun getById(id: Long): LocalMonitorLog?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(monitorLog: LocalMonitorLog)

    @Update
    suspend fun update(monitorLog: LocalMonitorLog)

    @Delete
    suspend fun delete(monitorLog: LocalMonitorLog)

}