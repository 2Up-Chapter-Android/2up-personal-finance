package com.twoup.personalfinance.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateTimeUtil {
    fun now(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun toEpochMillis(dateTime: LocalDateTime): Long {
        return dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }

    fun formatNoteDate(dateTime: LocalDateTime): String {
        val month = dateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }
        val day = if (dateTime.dayOfMonth < 10) "0${dateTime.dayOfMonth}" else dateTime.dayOfMonth
        val year = dateTime.year
        val hour = if (dateTime.hour < 10) "0${dateTime.hour}" else dateTime.hour
        val minute = if (dateTime.minute < 10) "0${dateTime.minute}" else dateTime.minute

        return buildString {
            append(month)
            append(" ")
            append(day)
            append(" ")
            append(year)
            append(", ")
            append(hour)
            append(":")
            append(minute)

        }
    }
    fun formatDateTrans(dateTime: LocalDateTime): String {
        val month = if (dateTime.monthNumber < 10) "0${dateTime.monthNumber}" else dateTime.monthNumber
        val year = dateTime.year
        return buildString {
            append(month)
            append(".")
            append(year)
        }
    }

    fun formatDateTransactionByMonth(dateTime: LocalDateTime): String {
        val month = dateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }
        val year = dateTime.year
        return buildString {
            append(month)
            append(" ")
            append(year)
        }
    }
    fun formatDateTransDays(dateTime: LocalDateTime): String {
        val day = if (dateTime.dayOfMonth < 10) "0${dateTime.dayOfMonth}" else dateTime.dayOfMonth
        return buildString {
            append(day)
        }
    }
    fun formatDateTransMonth(dateTime: LocalDateTime): String {
        val month = dateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }
        return buildString {
            append(month)
        }
    }
    fun formatTimeForAccountLast(dateTime: LocalDateTime): String {
        val yearNumber = (dateTime.year % 100).toString().padStart(2, '0')
        val year = dateTime.year
        val month = if (dateTime.monthNumber < 10) "0${dateTime.monthNumber}" else dateTime.monthNumber

        // Calculate the last day of the month manually
        val lastDayOfMonth = when (dateTime.month) {
            Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> "31"
            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> "30"
            else -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) "29" else "28"
        }

        return "$month.$lastDayOfMonth.$yearNumber"
    }
    fun formatTimeForAccountFirst(dateTime: LocalDateTime): String {
        val yearNumber = (dateTime.year % 100).toString().padStart(2, '0')
        val year = dateTime.year
        val month = if (dateTime.monthNumber < 10) "0${dateTime.monthNumber}" else dateTime.monthNumber

        // Calculate the last day of the month manually
        val lastDayOfMonth = when (dateTime.month) {
            Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> "31"
            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> "30"
            else -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) "29" else "28"
        }

        return "$month.1.$yearNumber"
    }

    fun countDownDays(deleteDateTime: LocalDateTime): Int {
        // Get the current time
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        // Calculate the time difference between deleteDateTime and now
        val diffInDays = calculateDaysDifference(deleteDateTime, now)

        val remainingDays = 30 - diffInDays

        return remainingDays.coerceIn(1, 30)
    }

    fun isNoteOld(deleteDateTime: LocalDateTime): Boolean {
        // Get the current time
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        // Calculate the time difference between deleteDateTime and now

        // Check if the time difference is exactly 30 days
        return when (calculateDaysDifference(deleteDateTime, now)) {
            30 -> true
            else -> false
        }
    }

    private fun calculateDaysDifference(dateTime1: LocalDateTime, dateTime2: LocalDateTime): Int {
        val daysInMonth1 = dateTime1.dayOfMonth + daysInYear(dateTime1.year) + if (isLeapYear(dateTime1.year) && dateTime1.month.ordinal > Month.FEBRUARY.ordinal) 1 else 0
        val daysInMonth2 = dateTime2.dayOfMonth + daysInYear(dateTime2.year) + if (isLeapYear(dateTime2.year) && dateTime2.month.ordinal > Month.FEBRUARY.ordinal) 1 else 0

        return (dateTime2.year - dateTime1.year) * 365 + (daysInMonth2 - daysInMonth1) + (dateTime2.month.ordinal - dateTime1.month.ordinal)
    }

    private fun daysInYear(year: Int): Int {
        return if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 1 else 0
    }

    private fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }
}