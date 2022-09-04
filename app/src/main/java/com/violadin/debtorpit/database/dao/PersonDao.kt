package com.violadin.debtorpit.database.dao

import androidx.room.*
import com.violadin.debtorpit.database.tables.Person

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person)

    @Delete
    fun deletePerson(person: Person)

    @Query("SELECT * FROM persons")
    fun getAllPersons(): List<Person>

//    @Query("SELECT * FROM persons WHERE first_name LIKE :first AND last_name LIKE :last")
//    fun findByName(first: String, last: String): Person

    @Query("SELECT * FROM persons WHERE id = :id")
    fun findById(id: Int): Person

    @Query("UPDATE persons SET debt = :debt WHERE id = :id")
    fun updatePerson(id: Int, debt: Double)
}