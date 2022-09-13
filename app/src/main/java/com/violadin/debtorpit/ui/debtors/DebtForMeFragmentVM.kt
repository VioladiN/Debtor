package com.violadin.debtorpit.ui.debtors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.violadin.debtorpit.database.dao.PersonDao
import com.violadin.debtorpit.database.tables.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
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
            personDao.getAllPersons().collect {
                _persons.value = it
            }
        }
    }
}