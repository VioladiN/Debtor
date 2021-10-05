package com.violadin.debtorpit.database.dao

import androidx.room.*
import com.violadin.debtorpit.domain.model.MyDebtPerson
import com.violadin.debtorpit.domain.model.Person
import io.reactivex.Flowable


@Dao
interface PersonDao {

    @Insert
    fun insertAllPersons(persons: List<Person>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person)

    @Delete
    fun deletePerson(person: Person)

    @Query("SELECT * FROM persons")
    fun getAllPersons(): Flowable<List<Person>>

    @Query("SELECT * FROM persons WHERE first_name LIKE :first AND last_name LIKE :last")
    fun findByName(first: String, last: String): Person

    @Query("SELECT * FROM persons WHERE id = :id")
    fun findById(id: Int): Person

    @Query("UPDATE persons SET debt = :debt WHERE id = :id")
    fun updatePerson(id: Int, debt: Double)


    @Query("SELECT * FROM my_debts")
    fun getAllPersonsMyDebt(): Flowable<List<MyDebtPerson>>

    @Query("UPDATE my_debts SET debt = :debt WHERE id = :id")
    fun updatePersonMyDebt(id: Int, debt: Double)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPersonMyDebt(person: MyDebtPerson)

    @Delete
    fun deletePersonMyDebt(person: MyDebtPerson)
}