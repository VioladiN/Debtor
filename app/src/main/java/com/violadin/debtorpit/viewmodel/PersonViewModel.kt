package com.violadin.debtorpit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.violadin.debtorpit.database.AppDataBase
import com.violadin.debtorpit.model.Person

class PersonViewModel(application: Application): AndroidViewModel(application) {
    private val db = AppDataBase.getInstance(application.applicationContext).personDao()
    internal val allPersons: LiveData<List<Person>> = db.getAllPErsons()

    fun insert(person: Person) {
        db.insertPerson(person)
    }

    fun delete(person: Person) {
        db.deletePerson(person)
    }

    fun update(person: Person, debt: Double) {
        person.firstName?.let { person.lastName?.let { it1 -> db.updatePerson(it, it1, debt) } }
    }
}