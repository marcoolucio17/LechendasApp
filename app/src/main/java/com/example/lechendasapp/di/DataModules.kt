package com.example.lechendasapp.di

import android.content.Context
import androidx.room.Room
import com.example.lechendasapp.data.repository.DefaultMonitorLogRepository
import com.example.lechendasapp.data.repository.DefaultUserRepository
import com.example.lechendasapp.data.repository.MonitorLogRepository
import com.example.lechendasapp.data.repository.UserRepository
import com.example.lechendasapp.data.source.local.AwaqDatabase
import com.example.lechendasapp.data.source.local.MonitorLogDao
import com.example.lechendasapp.data.source.local.UserDao
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
    abstract fun bindTaskRepository(repository: DefaultUserRepository): UserRepository

    @Singleton
    @Binds
    abstract fun bindMonitorLogRepository(repository: DefaultMonitorLogRepository): MonitorLogRepository
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
        ).build()
    }

    @Provides
    fun provideUserDao(database: AwaqDatabase): UserDao = database.userDao()

    @Provides
    fun provideMonitorLogDao(database: AwaqDatabase): MonitorLogDao = database.monitorLogDao()
    //TODO: inject all DAOs here
}