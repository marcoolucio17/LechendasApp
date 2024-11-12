package com.example.lechendasapp.di

import android.content.Context
import androidx.room.Room
import com.auth0.android.Auth0
import com.example.lechendasapp.data.repository.AnimalRepository
import com.example.lechendasapp.data.repository.ClimateRepository
import com.example.lechendasapp.data.repository.CoverageRepository
import com.example.lechendasapp.data.repository.DefaultAnimalRepository
import com.example.lechendasapp.data.repository.DefaultClimateRepository
import com.example.lechendasapp.data.repository.DefaultCoverageRepository
import com.example.lechendasapp.data.repository.DefaultMonitorLogRepository
import com.example.lechendasapp.data.repository.DefaultPhotoRepository
import com.example.lechendasapp.data.repository.DefaultTrapRepository
import com.example.lechendasapp.data.repository.DefaultUserRepository
import com.example.lechendasapp.data.repository.DefaultVegetationRepository
import com.example.lechendasapp.data.repository.MonitorLogRepository
import com.example.lechendasapp.data.repository.PhotoRepository
import com.example.lechendasapp.data.repository.TrapRepository
import com.example.lechendasapp.data.repository.UserRepository
import com.example.lechendasapp.data.repository.VegetationRepository
import com.example.lechendasapp.data.source.local.AwaqDatabase
import com.example.lechendasapp.data.source.local.MonitorLogDao
import com.example.lechendasapp.data.source.local.UserDao
import com.example.lechendasapp.data.source.local.AnimalDao
import com.example.lechendasapp.data.source.local.ClimateDao
import com.example.lechendasapp.data.source.local.CoverageDao
import com.example.lechendasapp.data.source.local.VegetationDao
import com.example.lechendasapp.data.source.local.TrapDao
import com.example.lechendasapp.data.source.local.PhotoDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(repository: DefaultUserRepository): UserRepository

    @Singleton
    @Binds
    abstract fun bindMonitorLogRepository(repository: DefaultMonitorLogRepository): MonitorLogRepository

    @Singleton
    @Binds
    abstract fun bindAnimalRepository(repository: DefaultAnimalRepository): AnimalRepository

    @Singleton
    @Binds
    abstract fun bindVegetationRepository(repository: DefaultVegetationRepository): VegetationRepository

    @Singleton
    @Binds
    abstract fun bindClimateRepository(repository: DefaultClimateRepository): ClimateRepository

    @Singleton
    @Binds
    abstract fun bindTrapRepository(repository: DefaultTrapRepository): TrapRepository

    @Singleton
    @Binds
    abstract fun bindCoverageRepository(repository: DefaultCoverageRepository): CoverageRepository

    @Singleton
    @Binds
    abstract fun bindPhotoRepository(repository: DefaultPhotoRepository): PhotoRepository
}


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AwaqDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AwaqDatabase::class.java,
            "Awaq.db"
        ).fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    fun provideUserDao(database: AwaqDatabase): UserDao = database.userDao()

    @Provides
    fun provideMonitorLogDao(database: AwaqDatabase): MonitorLogDao = database.monitorLogDao()

    @Provides
    fun provideAnimalDao(database: AwaqDatabase): AnimalDao = database.animalDao()

    @Provides
    fun provideClimateDao(database: AwaqDatabase): ClimateDao = database.climateDao()

    @Provides
    fun provideCoverageDao(database: AwaqDatabase): CoverageDao = database.coverageDao()

    @Provides
    fun provideVegetationDao(database: AwaqDatabase): VegetationDao = database.vegetationDao()

    @Provides
    fun provideTrapDao(database: AwaqDatabase): TrapDao = database.trapDao()

    @Provides
    fun providePhotoDao(database: AwaqDatabase): PhotoDao = database.photoDao()

}

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuth0(@ApplicationContext context: Context): Auth0 {
        return Auth0.getInstance(
            clientId = "QJbLlR2HCriN27HXC8XQYLqmu6eDcNMy",
            domain = "dev-fltwqcj6anshzwek.us.auth0.com",
        )
    }
}
