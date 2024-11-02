package com.example.lechendasapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

/*
exportSchema = True for production, False for development
*/

@Database(
    entities = [LocalUser::class, LocalMonitorLog::class, LocalPhoto::class, LocalAnimal::class,
        LocalClimate::class, LocalCoverage::class, LocalVegetation::class,
        LocalTrap::class],
    version = 4,
    exportSchema = false
)
abstract class AwaqDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun monitorLogDao(): MonitorLogDao
    abstract fun photoDao(): PhotoDao
    abstract fun animalDao(): AnimalDao
    abstract fun climateDao(): ClimateDao
    abstract fun coverageDao(): CoverageDao
    abstract fun vegetationDao(): VegetationDao
    abstract fun trapDao(): TrapDao

}