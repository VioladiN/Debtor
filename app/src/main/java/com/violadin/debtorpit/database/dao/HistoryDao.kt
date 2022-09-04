package com.violadin.debtorpit.database.dao

import androidx.room.*
import com.violadin.debtorpit.database.tables.History

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History)

    @Delete
    fun deleteHistory(history: History)

    @Query("SELECT * FROM history")
    fun getAllHistory(): List<History>

    @Query("SELECT * FROM history WHERE id_person = :id")
    fun getAllHistoryById(id: Int): List<History>

//    @Query("SELECT * FROM persons WHERE first_name LIKE :first AND last_name LIKE :last")
//    fun findByName(first: String, last: String): Person

//    @Query("SELECT * FROM history WHERE id_person = :id")
//    fun findById(id: Int): History

//    @Query("UPDATE persons SET debt = :debt WHERE id = :id")
//    fun updatePerson(id: Int, debt: Double)
}