package com.violadin.debtorpit.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.violadin.debtorpit.database.AppDataBase
import com.violadin.debtorpit.domain.model.Person
import io.reactivex.Flowable

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
        dao.updatePerson(id, newDebt)
    }

    private fun closeDb() {
        appDataBase.close()
    }

    override fun onCleared() {
        super.onCleared()
        closeDb()
    }
}