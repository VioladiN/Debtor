package com.violadin.debtorpit.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.violadin.debtorpit.database.tables.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person): Long

    @Delete
    fun deletePerson(person: Person)

    @Query("SELECT * FROM persons WHERE type = :type ORDER BY fio")
    fun getPersonsByType(type: String): Flow<List<Person>>

    @Query("SELECT * FROM persons WHERE id = :id")
    fun findById(id: Int): Flow<Person>

    @Query("UPDATE persons SET debt = :debt WHERE id = :id")
    fun updatePerson(id: Int, debt: Double)

    @Query("UPDATE persons SET fio = :fio, phone = :phone WHERE id = :id")
    fun updatePersonInfo(id: Int, fio: String, phone: String)

    @Query("SELECT SUM(debt) FROM persons WHERE type = :type")
    fun getTotalDebtsByType(type: String): Double
}