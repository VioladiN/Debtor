package com.violadin.debtorpit.database.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "persons")
data class Person(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "debt") val debt: Double?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "created_time") val created_time: String?,
    @ColumnInfo(name = "type") val type: String?
): Serializable