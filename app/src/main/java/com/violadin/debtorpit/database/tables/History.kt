package com.violadin.debtorpit.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "id_person") val id_person: Int? = null,
    @ColumnInfo(name = "amount") val amount: Double? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "created_time") val createdTime: Long? = null,
    @ColumnInfo(name = "type") val type: String? = null
)