package com.violadin.debtorpit.ui.multipydebts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.violadin.debtorpit.database.dao.PersonDao
import com.violadin.debtorpit.database.tables.Person
import com.violadin.debtorpit.enums.PersonType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultiDebtFragmentVM @Inject constructor(
    private val personDao: PersonDao
) : ViewModel() {

    private val _persons = MutableStateFlow<List<ChoosePersonModel>>(emptyList())
    val persons: StateFlow<List<ChoosePersonModel>> = _persons.asStateFlow()

    init {
        getMyDebtPersons()
    }

    private fun getMyDebtPersons() {
        viewModelScope.launch(Dispatchers.IO) {
            personDao.getAllPersons().collect { persons ->
                _persons.value = persons.filter { it.type == PersonType.DEBT_FOR_ME_PERSON.type }
                    .map { ChoosePersonModel(it) }
            }
        }
    }

    fun updatePersons(persons: List<ChoosePersonModel>, meToo: Boolean, debt: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val countPersons = if (meToo) persons.size + 1 else persons.size
            val amount = (debt / countPersons).toDouble()
            persons.filter { it.isChecked }.forEach { person ->
                personDao.updatePerson(person.person.id!!, (person.person.debt!! + amount))
            }
        }
    }
}