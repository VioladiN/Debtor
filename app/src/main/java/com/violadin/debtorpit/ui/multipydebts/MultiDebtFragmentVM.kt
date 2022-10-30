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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultiDebtFragmentVM @Inject constructor(
    private val personDao: PersonDao
) : ViewModel() {

    private val _persons = MutableStateFlow<List<ChoosePersonModel>>(emptyList())
    val persons: StateFlow<List<ChoosePersonModel>> = _persons

    init {
        getMyDebtPersons()
    }

    private fun getMyDebtPersons() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = ArrayList<ChoosePersonModel>()
            personDao.getAllPersons().collect { persons ->
                persons.forEach { person ->
                    if (person.type == PersonType.DEBT_FOR_ME_PERSON.type)
                        result.add(ChoosePersonModel(person))
                }
                _persons.value = result
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