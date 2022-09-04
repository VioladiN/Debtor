package com.violadin.debtorpit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.violadin.debtorpit.database.dao.PersonDao
import com.violadin.debtorpit.database.tables.History
import com.violadin.debtorpit.database.tables.Person

@Database(entities = [Person::class, History::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun personDao(): PersonDao

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDataBase(context).also { INSTANCE = it }
            }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java, "application.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}