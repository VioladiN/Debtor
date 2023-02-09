package com.violadin.debtorpit.database.dao

import androidx.room.*
import com.violadin.debtorpit.database.tables.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History)

    @Delete
    fun deleteHistory(history: History)

    @Query("SELECT * FROM history")
    fun getAllHistory(): Flow<List<History>>

    @Query("SELECT * FROM history WHERE id_person = :id")
    fun getAllHistoryById(id: Int): Flow<List<History>>

    @Query("DELETE FROM history WHERE id_person = :id")
    fun  deleteHistoryOfPerson(id: Int)
}