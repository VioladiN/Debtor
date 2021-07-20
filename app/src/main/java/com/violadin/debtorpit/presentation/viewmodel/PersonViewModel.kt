package com.violadin.debtorpit.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.violadin.debtorpit.database.AppDataBase
import com.violadin.debtorpit.domain.model.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDataBase.getInstance(application.applicationContext).personDao()
    val data: LiveData<List<Person>> = db.getAllPersons()

    fun insert(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            db.insertPerson(person)
        }
    }

    fun delete(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            db.deletePerson(person)
        }
    }

    fun update(person: Person, debt: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            person.firstName?.let { person.lastName?.let { it1 -> db.updatePerson(it, it1, debt) } }
        }
    }
}