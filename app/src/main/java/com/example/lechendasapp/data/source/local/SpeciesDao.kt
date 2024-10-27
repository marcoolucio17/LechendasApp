package com.example.lechendasapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeciesDao {

    //Basic methods

    @Query("SELECT * FROM species")
    fun observeAll(): Flow<List<LocalSpecies>>

    @Query("SELECT * FROM species WHERE id = :id")
    fun observeById(id: Int): Flow<LocalSpecies>

    @Query("SELECT * FROM species")
    suspend fun getAll(): List<LocalSpecies>

    @Query("SELECT * FROM species WHERE id = :id")
    suspend fun getById(id: Int): LocalSpecies?

    @Insert
    suspend fun insert(species: LocalSpecies)

    @Update
    suspend fun update(species: LocalSpecies)

    @Delete
    suspend fun delete(species: LocalSpecies)


}