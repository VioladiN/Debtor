package com.violadin.debtorpit.ui.multipydebts

import android.os.Parcelable
import com.violadin.debtorpit.database.tables.Person
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChoosePersonModel(
    val person: Person,
    var isChecked: Boolean = false
): Parcelable