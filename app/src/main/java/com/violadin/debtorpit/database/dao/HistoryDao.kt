package com.violadin.debtorpit.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.violadin.debtorpit.database.tables.History
import com.violadin.debtorpit.enums.PersonType
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History)

    @Query("SELECT * FROM history WHERE personType = :personType ORDER BY createdTime DESC")
    fun getAllHistoryFilteredByPersonType(personType: String = PersonType.DEBT_FOR_ME_PERSON.type): Flow<List<History>>

    @Query("SELECT * FROM history WHERE personId = :id ORDER BY createdTime")
    fun getAllHistoryById(id: Int): Flow<List<History>>

    @Query("DELETE FROM history WHERE personId = :id")
    fun deleteHistoryOfPerson(id: Int)

    @Query("SELECT * FROM history WHERE createdTime >= :dateFrom AND createdTime <= :dateTo AND personType = :personType ORDER BY createdTime DESC")
    fun getFilteredHistoriesByDates(
        dateFrom: Long, dateTo: Long, personType: String = PersonType.DEBT_FOR_ME_PERSON.type
    ): Flow<List<History>>
}