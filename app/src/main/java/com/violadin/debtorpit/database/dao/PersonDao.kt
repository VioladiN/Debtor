package com.violadin.debtorpit.database.dao

import androidx.room.*
import com.violadin.debtorpit.database.tables.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person)

    @Delete
    fun deletePerson(person: Person)

    @Query("SELECT * FROM persons")
    fun getAllPersons(): Flow<List<Person>>

    @Query("SELECT * FROM persons WHERE id = :id")
    fun findById(id: Int): Flow<Person>

    @Query("UPDATE persons SET debt = :debt WHERE id = :id")
    fun updatePerson(id: Int, debt: Double)
}