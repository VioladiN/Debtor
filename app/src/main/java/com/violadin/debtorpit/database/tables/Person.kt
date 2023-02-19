package com.violadin.debtorpit.database.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
@Entity(tableName = "persons")
data class Person(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "fio") val fio: String? = null,
    @ColumnInfo(name = "debt") val debt: Double? = null,
    @ColumnInfo(name = "phone") val phone: String? = null,
    @ColumnInfo(name = "created_time") val createdTime: Long? = null,
    @ColumnInfo(name = "type") val type: String? = null
): Parcelable