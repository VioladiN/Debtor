package com.violadin.debtorpit.di

import android.content.Context
import androidx.room.Room
import com.violadin.debtorpit.database.AppDataBase
import com.violadin.debtorpit.database.dao.HistoryDao
import com.violadin.debtorpit.database.dao.PersonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModule {

    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java, "application.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providePersonDao(appDataBase: AppDataBase): PersonDao = appDataBase.personDao()

    @Provides
    @Singleton
    fun provideHistoryDao(appDataBase: AppDataBase): HistoryDao = appDataBase.historyDao()

}