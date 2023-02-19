package com.violadin.debtorpit.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "personId") val personId: Int? = null,
    @ColumnInfo(name = "amount") val amount: Double? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "createdTime") val createdTime: Long? = null,
    @ColumnInfo(name = "debtType") val debtType: String? = null,
    @ColumnInfo(name = "personType") val personType: String? = null,
    @ColumnInfo(name = "personName") val personName: String? = null
)