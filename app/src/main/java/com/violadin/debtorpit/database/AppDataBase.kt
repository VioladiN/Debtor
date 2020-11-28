package com.violadin.debtorpit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.violadin.debtorpit.database.dao.PersonDao
import com.violadin.debtorpit.model.Person
import java.util.concurrent.Executors

@Database(entities = arrayOf(Person::class), version = 4)
abstract class AppDataBase : RoomDatabase() {
    abstract fun personDao(): PersonDao

    companion object {

        private var NUMBER_OF_THREADS = 4
        public var databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS)
        
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