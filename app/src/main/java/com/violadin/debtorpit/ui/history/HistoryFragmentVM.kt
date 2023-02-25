package com.violadin.debtorpit.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.violadin.debtorpit.database.dao.HistoryDao
import com.violadin.debtorpit.database.dao.PersonDao
import com.violadin.debtorpit.database.tables.History
import com.violadin.debtorpit.database.tables.Person
import com.violadin.debtorpit.enums.PersonType
import com.violadin.debtorpit.utils.DAY_MONTH_YEAR_PATTERN
import com.violadin.debtorpit.utils.stringTimeToLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class HistoryFragmentVM @Inject constructor(
    private val historyDao: HistoryDao
) : ViewModel() {

    private val _histories = MutableStateFlow<List<History>>(emptyList())
    val histories: StateFlow<List<History>> = _histories

    fun getHistoriesByDates(dateFrom: String, dateTo: String, myDebt: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            historyDao.getFilteredHistoriesByDates(
                stringTimeToLong(dateFrom, DAY_MONTH_YEAR_PATTERN),
                stringTimeToLong(dateTo, DAY_MONTH_YEAR_PATTERN, 1),
                if (myDebt) PersonType.MY_DEBT_PERSON.type else PersonType.DEBT_FOR_ME_PERSON.type
            ).collect { histories ->
                _histories.value = histories
            }
        }
    }

    fun getAllHistoryFilteredByPersonType(myDebt: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            historyDao.getAllHistoryFilteredByPersonType(
                if (myDebt)
                    PersonType.MY_DEBT_PERSON.type
                else
                    PersonType.DEBT_FOR_ME_PERSON.type
            ).collect { histories ->
                    _histories.value = histories
                }
        }
    }
}