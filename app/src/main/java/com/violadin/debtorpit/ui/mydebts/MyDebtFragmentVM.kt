package com.violadin.debtorpit.ui.mydebts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.violadin.debtorpit.database.dao.PersonDao
import com.violadin.debtorpit.database.tables.Person
import com.violadin.debtorpit.enums.PersonType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyDebtFragmentVM @Inject constructor(
    private val personDao: PersonDao
) : ViewModel() {

    private val _persons = MutableStateFlow<List<Person>>(emptyList())
    val persons: StateFlow<List<Person>> = _persons

    init {
        getMyDebtPersons()
    }

    private fun getMyDebtPersons() {
        viewModelScope.launch(Dispatchers.IO) {
            personDao.getAllPersons().collect { persons ->
                _persons.value = persons.filter { it.type == PersonType.MY_DEBT_PERSON.type }
            }
        }
    }

}