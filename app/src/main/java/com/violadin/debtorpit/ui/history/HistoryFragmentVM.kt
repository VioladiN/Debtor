package com.violadin.debtorpit.ui.history

import androidx.lifecycle.ViewModel
import com.violadin.debtorpit.database.dao.HistoryDao
import com.violadin.debtorpit.database.dao.PersonDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryFragmentVM @Inject constructor(
    private val personDao: PersonDao,
    private val historyDao: HistoryDao
) : ViewModel() {



}