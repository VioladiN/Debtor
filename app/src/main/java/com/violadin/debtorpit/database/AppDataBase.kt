package com.violadin.debtorpit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.violadin.debtorpit.database.dao.HistoryDao
import com.violadin.debtorpit.database.dao.PersonDao
import com.violadin.debtorpit.database.tables.History
import com.violadin.debtorpit.database.tables.Person

@Database(entities = [Person::class, History::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun personDao(): PersonDao

    abstract fun historyDao(): HistoryDao
}