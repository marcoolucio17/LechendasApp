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

    @Query("SELECT * FROM photos WHERE monitor_log_id = :monitorLogId AND forms_id = :formsId")
    fun observeByMonitorFormsId(monitorLogId: Long, formsId: Long): Flow<List<LocalPhoto>>

    @Query("SELECT * FROM photos WHERE monitor_log_id = -1 AND forms_id = -1")
    fun observeByNull(): Flow<List<LocalPhoto>>

    @Query("SELECT * FROM photos WHERE id = :id")
    fun observeById(id: Long): Flow<LocalPhoto>

    @Query("SELECT * FROM photos")
    suspend fun getAll(): List<LocalPhoto>

    @Query("SELECT * FROM photos WHERE id = :id")
    suspend fun getById(id: Long): LocalPhoto?

    @Insert
    suspend fun insert(photo: LocalPhoto)

    @Update
    suspend fun update(photo: LocalPhoto)

    @Delete
    suspend fun delete(photo: LocalPhoto)

    @Query("DELETE FROM photos WHERE monitor_log_id = -1 AND forms_id = -1")
    suspend fun deleteByNull()

    //Count
    @Query("SELECT COUNT(*) FROM photos")
    suspend fun count(): Int

    @Query("DELETE FROM photos WHERE monitor_log_id = :monitorLogId")
    suspend fun deleteByMonitorFormsId(monitorLogId: Long)

}