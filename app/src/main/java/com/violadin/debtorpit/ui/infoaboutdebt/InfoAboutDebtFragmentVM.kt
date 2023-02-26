package com.violadin.debtorpit.ui.infoaboutdebt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.violadin.debtorpit.database.dao.HistoryDao
import com.violadin.debtorpit.database.dao.PersonDao
import com.violadin.debtorpit.database.tables.History
import com.violadin.debtorpit.database.tables.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoAboutDebtFragmentVM @Inject constructor(
    private val personDao: PersonDao,
    private val historyDao: HistoryDao
) : ViewModel() {

    private val _person = MutableStateFlow(Person())
    val person = _person.asStateFlow()

    private val _deletePersonResult = MutableStateFlow(false)
    val deletePersonResult = _deletePersonResult.asStateFlow()

    private val _history = MutableStateFlow<List<History>>(emptyList())
    val history = _history.asStateFlow()

    fun getPersonById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            personDao.findById(id).collect {
                _person.value = it
            }
        }
    }

    fun changeDebtOfPerson(
        id: Int,
        debt: Double,
        type: String,
        amount: Double,
        description: String,
        date: Long,
        personType: String,
        personName: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                personDao.updatePerson(id, debt)
                historyDao.insertHistory(
                    History(
                        personId = id,
                        amount = amount,
                        description = description,
                        createdTime = date,
                        debtType = type,
                        personType = personType,
                        personName = personName
                    )
                )
            }
        }
    }

    fun deletePerson(person: Person, job: Job?) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                historyDao.deleteHistoryOfPerson(person.id!!)
                personDao.deletePerson(person)
            }.onSuccess {
                job?.let {
                    job.cancel()
                }
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

    fun getHistory(personId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                historyDao.getAllHistoryById(personId).collect {
                    _history.value = it
                }
            }
        }
    }
}