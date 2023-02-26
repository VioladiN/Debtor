package com.violadin.debtorpit.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.violadin.debtorpit.database.dao.PersonDao
import com.violadin.debtorpit.enums.PersonType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsFragmentVM @Inject constructor(
    private val personDao: PersonDao
) : ViewModel() {

    private val _totalDebts = MutableStateFlow<Pair<Double, Double>?>(null)
    val totalDebts = _totalDebts.asStateFlow()

    init {
        getMyDebtsTotal()
    }
    private fun getMyDebtsTotal() {
        viewModelScope.launch(Dispatchers.IO) {
            val myDebts = async {
                personDao.getTotalDebtsByType(PersonType.MY_DEBT_PERSON.type)
            }
            val debtsForMe = async {
                personDao.getTotalDebtsByType(PersonType.DEBT_FOR_ME_PERSON.type)
            }
            _totalDebts.value = Pair(myDebts.await(), debtsForMe.await())
        }
    }
}