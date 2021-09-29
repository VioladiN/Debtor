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

    private fun closeDb() {
        appDataBase.close()
    }

    override fun onCleared() {
        super.onCleared()
        closeDb()
    }
}