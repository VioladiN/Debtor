package com.violadin.debtorpit.ui.multidebts

import android.os.Parcelable
import com.violadin.debtorpit.database.tables.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChoosePersonModel(
    val person: Person,
    var isChecked: Boolean = false
): Parcelable