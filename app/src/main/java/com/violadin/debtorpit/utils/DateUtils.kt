package com.violadin.debtorpit.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone

private const val FULL_MONTH_PATTERN = "dd MMMM yyyy"
const val DAY_MONTH_YEAR_PATTERN = "dd.MM.yyyy"

enum class DateType(val dateType: String) {
    DAY("day"),
    MONTH("month"),
    YEAR("year")
}

val currentTime: ZonedDateTime
    get() = LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId())

fun longTimeToString(time: Long, pattern: String = FULL_MONTH_PATTERN): String {
    return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault())
        .toLocalDate().format(DateTimeFormatter.ofPattern(pattern))
}

fun stringTimeToLong(
    string: String,
    pattern: String = FULL_MONTH_PATTERN,
    increaseDays: Long = 0
): Long {
    return LocalDate.parse(string, DateTimeFormatter.ofPattern(pattern)).plusDays(increaseDays)
        .atStartOfDay(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli()
}

fun stringCurrentTime(pattern: String = FULL_MONTH_PATTERN): String {
    return currentTime.format(DateTimeFormatter.ofPattern(pattern))
}

fun longCurrentTime(): Long {
    return currentTime.toInstant().toEpochMilli()
}