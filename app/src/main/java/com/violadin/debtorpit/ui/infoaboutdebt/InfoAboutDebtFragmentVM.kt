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

    private val _person = MutableStateFlow(Person())
    val person = _person.asStateFlow()

    fun getPersonById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            personDao.findById(id).collect {
                _person.value = it
            }
        }
    }
}