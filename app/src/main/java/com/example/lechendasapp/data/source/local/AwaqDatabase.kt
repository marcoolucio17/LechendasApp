package com.example.lechendasapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

/*
exportSchema = True for production, False for development
*/

@Database(entities = [LocalUser::class, LocalMonitorLog::class, LocalSpecies::class, LocalPhoto::class], version = 1, exportSchema = false)
abstract class AwaqDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun monitorLogDao() : MonitorLogDao
    abstract fun speciesDao() : SpeciesDao
    abstract fun photoDao() : PhotoDao

}