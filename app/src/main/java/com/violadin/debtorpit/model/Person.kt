package com.violadin.debtorpit.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Person(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "first_name") val firstName:String?,
        @ColumnInfo(name = "last_name") val lastName:String?,
        @ColumnInfo(name = "debt") val debt:Float? ) {

}