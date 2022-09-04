package com.violadin.debtorpit.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "id_person") val id_person: Int?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "created_time") val created_time: String?,
    @ColumnInfo(name = "type") val type: String?
)