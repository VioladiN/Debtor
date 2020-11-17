package com.violadin.debtorpit.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Person::class), version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun personDao(): PersonDao

    companion object {
        
        @Volatile private var INSTANCE: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDataBase(context).also { INSTANCE = it}
                }
        
        private fun buildDataBase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDataBase::class.java, "app.db")
                        .build()
    }
}