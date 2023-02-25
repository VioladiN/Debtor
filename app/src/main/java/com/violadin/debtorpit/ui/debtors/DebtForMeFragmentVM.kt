package com.violadin.debtorpit.ui.debtors

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
class DebtForMeFragmentVM @Inject constructor(
    private val personDao: PersonDao
) : ViewModel() {

    private val _persons = MutableStateFlow<List<Person>>(emptyList())
    val persons: StateFlow<List<Person>> = _persons

    init {
        getDebtForMePersons()
    }

    private fun getDebtForMePersons() {
        viewModelScope.launch(Dispatchers.IO) {
            personDao.getPersonsByType(PersonType.DEBT_FOR_ME_PERSON.type).collect { persons ->
                _persons.value = persons
            }
        }
    }
}