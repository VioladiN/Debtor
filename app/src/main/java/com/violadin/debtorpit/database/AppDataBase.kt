package com.violadin.debtorpit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.violadin.debtorpit.database.dao.HistoryDao
import com.violadin.debtorpit.database.dao.PersonDao
import com.violadin.debtorpit.database.tables.History
import com.violadin.debtorpit.database.tables.Person

@Database(entities = [Person::class, History::class], version = 2)
abstract class AppDataBase : RoomDatabase() {

    abstract fun personDao(): PersonDao

    abstract fun historyDao(): HistoryDao
}