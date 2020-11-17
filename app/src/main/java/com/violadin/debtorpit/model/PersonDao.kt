package com.violadin.debtorpit.model

import androidx.room.*

@Dao
interface PersonDao {

    @Insert
    fun insertAllPersons(persons: List<Person>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person)

    @Delete
    fun deletePerson(person: Person)

    @Query("SELECT * FROM persons WHERE first_name LIKE :first AND " +
        "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Person
}