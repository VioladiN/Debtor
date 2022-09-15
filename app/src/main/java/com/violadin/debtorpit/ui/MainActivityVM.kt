package com.violadin.debtorpit.ui

import androidx.lifecycle.ViewModel
import com.violadin.debtorpit.database.AppDataBase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    val appDataBase: AppDataBase
) : ViewModel() {

}