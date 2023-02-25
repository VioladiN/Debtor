package com.violadin.debtorpit.ui.createdebt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.violadin.debtorpit.database.dao.HistoryDao
import com.violadin.debtorpit.database.dao.PersonDao
import com.violadin.debtorpit.database.tables.History
import com.violadin.debtorpit.database.tables.Person
import com.violadin.debtorpit.enums.DebtType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateDebtFragmentVM @Inject constructor(
    private val personDao: PersonDao,
    private val historyDao: HistoryDao
) : ViewModel() {

    fun createPerson(person: Person, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val personId = personDao.insertPerson(person)

            if (person.debt!!.toDouble() > 0.0) {
                historyDao.insertHistory(History(
                    personId = personId.toInt(),
                    amount = person.debt,
                    description = description,
                    createdTime = person.createdTime,
                    debtType = DebtType.INCREASE.type,
                    personType = person.type,
                    personName = person.fio
                ))
            }
        }
    }

}