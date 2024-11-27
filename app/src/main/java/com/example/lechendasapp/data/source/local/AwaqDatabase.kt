package com.example.lechendasapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

/*
exportSchema = True for production, False for development
*/

@Database(
    entities = [LocalUser::class, LocalMonitorLog::class, LocalPhoto::class, LocalAnimal::class,
        LocalClimate::class, LocalCoverage::class, LocalVegetation::class,
        LocalTrap::class, LocalAuthToken::class],
    version = 11,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AwaqDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun monitorLogDao(): MonitorLogDao
    abstract fun photoDao(): PhotoDao
    abstract fun animalDao(): AnimalDao
    abstract fun climateDao(): ClimateDao
    abstract fun coverageDao(): CoverageDao
    abstract fun vegetationDao(): VegetationDao
    abstract fun trapDao(): TrapDao
    abstract fun authTokenDao(): AuthTokenDao

}

class DateConverter {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}