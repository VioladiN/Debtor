package com.violadin.debtorpit.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.violadin.debtorpit.domain.model.Person

@Dao
interface PersonDao {

    @Insert
    fun insertAllPersons(persons: List<Person>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person)

    @Delete
    fun deletePerson(person: Person)

    @Query("SELECT * FROM persons")
    fun getAllPersons(): LiveData<List<Person>>

    @Query("SELECT * FROM persons WHERE first_name LIKE :first AND last_name LIKE :last")
    fun findByName(first: String, last: String): Person

    @Query("SELECT * FROM persons WHERE id LIKE :id")
    fun findById(id: Int): Person

    @Query("UPDATE persons SET debt = :debt WHERE first_name LIKE :first AND last_name LIKE :last")
    fun updatePerson(first: String, last: String, debt: Double)
}