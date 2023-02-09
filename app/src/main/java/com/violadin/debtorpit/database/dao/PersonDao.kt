package com.violadin.debtorpit.database.dao

import androidx.room.*
import com.violadin.debtorpit.database.tables.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person): Long

    @Delete
    fun deletePerson(person: Person)

    @Query("SELECT * FROM persons")
    fun getAllPersons(): Flow<List<Person>>

    @Query("SELECT * FROM persons WHERE id = :id")
    fun findById(id: Int): Flow<Person>

    @Query("UPDATE persons SET debt = :debt WHERE id = :id")
    fun updatePerson(id: Int, debt: Double)

    @Query("UPDATE persons SET fio = :fio, phone = :phone WHERE id = :id")
    fun updatePersonInfo(id: Int, fio: String, phone: String)
}