package com.violadin.debtorpit.ui.infoaboutdebt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.violadin.debtorpit.database.dao.PersonDao
import com.violadin.debtorpit.database.tables.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoAboutDebtFragmentVM @Inject constructor(
    private val personDao: PersonDao
) : ViewModel() {

    private val _person = MutableStateFlow<Person?>(null)
    val person = _person.asStateFlow()

    private val _updateDebtResult = MutableStateFlow("")
    val updateDebtResult = _updateDebtResult.asStateFlow()

    private val _deletePersonResult = MutableStateFlow(false)
    val deletePersonResult = _deletePersonResult.asStateFlow()

    fun getPersonById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            personDao.findById(id).collect {
                _person.value = it
            }
        }
    }

    fun changeDebtOfPerson(id: Int, debt: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                personDao.updatePerson(id, debt)
            }.onSuccess {
                _updateDebtResult.value = "Хорошо"
            }.onFailure {
                _updateDebtResult.value = "Плохо"
            }
        }
    }

    fun deletePerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                personDao.deletePerson(person)
            }.onSuccess {
                _deletePersonResult.value = true
            }
        }
    }

    fun updatePersonInfo(id: Int, fio: String, phone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                personDao.updatePersonInfo(id, fio, phone)
            }
        }
    }
}