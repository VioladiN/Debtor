package com.violadin.debtorpit.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.violadin.debtorpit.database.AppDataBase
import com.violadin.debtorpit.database.tables.MyDebtPerson
import com.violadin.debtorpit.database.tables.Person
import io.reactivex.Flowable
import java.math.RoundingMode

class PersonViewModel(application: Application) : AndroidViewModel(application) {

    private val appDataBase = AppDataBase.getInstance(application.applicationContext)
    private val dao = appDataBase.personDao()

    fun getAllPersons(): Flowable<List<Person>> =
        dao.getAllPersons()

    fun addPerson(person: Person) {
        dao.insertPerson(person)
    }

    fun deletePerson(person: Person) {
        dao.deletePerson(person)
    }

    fun updatePerson(id: Int, newDebt: Double) {
        dao.updatePerson(id, newDebt.toBigDecimal().setScale(2, RoundingMode.UP).toDouble())
    }

    fun insertPersonMyDebt(person: MyDebtPerson) {
        dao.insertPersonMyDebt(person)
    }

    fun getAllPersonMyDebt():Flowable<List<MyDebtPerson>> =
        dao.getAllPersonsMyDebt()

    fun updatePersonMyDebt(id: Int, newDebt: Double) {
        dao.updatePersonMyDebt(id, newDebt.toBigDecimal().setScale(2, RoundingMode.UP).toDouble())
    }

    fun deletePersonMyDebt(person: MyDebtPerson) {
        dao.deletePersonMyDebt(person)
    }

    fun closeDb() {
        appDataBase.close()
    }
}