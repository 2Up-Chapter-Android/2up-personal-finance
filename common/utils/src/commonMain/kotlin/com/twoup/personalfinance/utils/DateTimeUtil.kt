package com.twoup.personalfinance.utils

import com.twoup.personalfinance.utils.DateTimeUtil.getWeeksInMonth
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
        val month =
            if (dateTime.monthNumber < 10) "0${dateTime.monthNumber}" else dateTime.monthNumber
        val year = dateTime.year
        return buildString {
            append(month)
            append(".")
            append(year)
        }
    }

//    fun formatDateTransactionByMonth(dateTime: LocalDateTime): String {
//        val month = dateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }
//        val year = dateTime.year
//        return buildString {
//            append(month)
//            append(" ")
//            append(year)
//        }
//    }

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
        val month =
            if (dateTime.monthNumber < 10) "0${dateTime.monthNumber}" else dateTime.monthNumber

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
        val month =
            if (dateTime.monthNumber < 10) "0${dateTime.monthNumber}" else dateTime.monthNumber
        return "$month.1.$yearNumber"
    }

//    fun countDownDays(deleteDateTime: LocalDateTime): Int {
//        // Get the current time
//        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
//
//        // Calculate the time difference between deleteDateTime and now
//        val diffInDays = calculateDaysDifference(deleteDateTime, now)
//
//        val remainingDays = 30 - diffInDays
//
//        return remainingDays.coerceIn(1, 30)
//    }

//    fun isNoteOld(deleteDateTime: LocalDateTime): Boolean {
//        // Get the current time
//        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
//
//        // Calculate the time difference between deleteDateTime and now
//
//        // Check if the time difference is exactly 30 days
//        return when (calculateDaysDifference(deleteDateTime, now)) {
//            30 -> true
//            else -> false
//        }
//    }

    private fun calculateDaysDifference(dateTime1: LocalDateTime, dateTime2: LocalDateTime): Int {
        val daysInMonth1 =
            dateTime1.dayOfMonth + daysInYear(dateTime1.year) + if (isLeapYear(dateTime1.year) && dateTime1.month.ordinal > Month.FEBRUARY.ordinal) 1 else 0
        val daysInMonth2 =
            dateTime2.dayOfMonth + daysInYear(dateTime2.year) + if (isLeapYear(dateTime2.year) && dateTime2.month.ordinal > Month.FEBRUARY.ordinal) 1 else 0

        return (dateTime2.year - dateTime1.year) * 365 + (daysInMonth2 - daysInMonth1) + (dateTime2.month.ordinal - dateTime1.month.ordinal)
    }

    private fun daysInYear(year: Int): Int {
        return if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 1 else 0
    }

    private fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }

    // lấy ra list của tuần của tháng
    fun getWeeksInMonth(
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
        daysInWeek: Int
    ): List<List<LocalDateTime>> {
        val weeks = mutableListOf<List<LocalDateTime>>()

        var currentDateTime = startDateTime
        val currentWeek = mutableListOf<LocalDateTime>()

        while (currentDateTime <= endDateTime) {
            currentWeek.add(currentDateTime)

            if (currentWeek.size == daysInWeek || currentDateTime == endDateTime) {
                weeks.add(currentWeek.toList())
                currentWeek.clear()
            }

            currentDateTime = addDays(currentDateTime, 1)
        }

        return weeks
    }

    //them 1 ngày
    private fun addDays(dateTime: LocalDateTime, daysToAdd: Int): LocalDateTime {
        var year = dateTime.year
        var month = dateTime.month
        var dayOfMonth = dateTime.dayOfMonth
        val hour = dateTime.hour
        val minute = dateTime.minute
        val second = dateTime.second

        for (i in 1..daysToAdd) {
            // Increment day by 1
            dayOfMonth++

            // Check if day exceeds the maximum for the current month
            if (dayOfMonth > getLastDayOfMonth(year, month)) {
                dayOfMonth = 1
                month =
                    if (month.ordinal == Month.DECEMBER.ordinal) Month.JANUARY else Month.values()[month.ordinal + 1]

                // Check if month is January, increment year
                if (month == Month.JANUARY) {
                    year++
                }
            }
        }

        return LocalDateTime(year, month, dayOfMonth, hour, minute, second)
    }

    // them ngày cuối của tháng
    private fun getLastDayOfMonth(year: Int, month: Month): Int {
        val lastDay = when (month) {
            Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
            else -> {
                if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            }
        }
        return lastDay
    }

    fun getFirstAndLastDayOfMonth(year: Int, month: Int): Pair<LocalDateTime, LocalDateTime> {
        require(month in 1..12) { "Invalid month: $month. Month should be between 1 and 12." }

        val firstDay = LocalDateTime(year, Month(month), 1, 0, 0)

        val lastDayOfMonth = when (Month(month)) {
            Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
            else -> {
                if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            }
        }

        val lastDay = LocalDateTime(year, Month(month), lastDayOfMonth, 23, 59, 59)

        return Pair(firstDay, lastDay)
    }

    fun formatTimeForTheFirstOfWeek(dateTime: List<LocalDateTime>): String {
        val monthFirst =
            if (dateTime.first().monthNumber < 10) "0${dateTime.first().monthNumber}" else dateTime.first().monthNumber
        val monthLast =
            if (dateTime.last().monthNumber < 10) "0${dateTime.last().monthNumber}" else dateTime.last().monthNumber

        val dayFirst = dateTime.first().dayOfMonth

        val dayLast = dateTime.last().dayOfMonth

        return "$monthFirst.$dayFirst ~ $monthLast.$dayLast"
    }

    fun getAllDaysInMonth(year: Int, month: Int): List<LocalDateTime> {
        val firstDayOfMonth = LocalDateTime(year, month, 1, 0, 0)
        val lastDayOfMonth =
            LocalDateTime(year, month, getLastDayOfMonthForCalendar(year, month), 23, 59)
        val allDays = mutableListOf<LocalDateTime>()
        var currentDay = firstDayOfMonth
        while (currentDay <= lastDayOfMonth) {
            allDays.add(currentDay)
            currentDay = addDays(currentDay, 1)
        }
        return allDays
    }

    fun getAllDaysInMonthBefore(year: Int, month: Int): List<LocalDateTime> {
        val allDays = mutableListOf<LocalDateTime>()
        val adjustedMonth = if (month == 1) {
            12
        } else {
            month - 1
        }
        val adjustedYear = if (month == 1) {
            year - 1
        } else {
            year
        }
        val firstDayOfMonth = LocalDateTime(adjustedYear, adjustedMonth, 1, 0, 0)
        val lastDayOfMonth =
            LocalDateTime(
                adjustedYear,
                adjustedMonth,
                getLastDayOfMonthForCalendar(adjustedYear, adjustedMonth),
                23,
                59
            )
        var currentDay = firstDayOfMonth
        while (currentDay <= lastDayOfMonth) {
            allDays.add(currentDay)
            currentDay = addDays(currentDay, 1)
        }
        return allDays
    }

    fun getAllDaysInMonthAfter(year: Int, month: Int): List<LocalDateTime> {
        val allDays = mutableListOf<LocalDateTime>()
        val adjustedMonth = if (month == 12) 1 else month + 1
        val adjustedYear = if (month == 12) year + 1 else year

        val firstDayOfMonth = LocalDateTime(adjustedYear, adjustedMonth, 1, 0, 0)
        val lastDayOfMonth =
            LocalDateTime(
                adjustedYear,
                adjustedMonth,
                getLastDayOfMonthForCalendar(adjustedYear, adjustedMonth),
                23,
                59
            )

        var currentDay = firstDayOfMonth
        while (currentDay <= lastDayOfMonth) {
            allDays.add(currentDay)
            currentDay = addDays(currentDay, 1)
        }
        return allDays
    }




    private fun getLastDayOfMonthForCalendar(year: Int, month: Int): Int {
        val lastDay = when (Month.values()[month - 1]) {
            Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
            else -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
        }
        return lastDay
    }

    fun formatFirstAndLastDayOfMonthRange(year: Int, month: Int): String {
        require(month in 1..12) { "Invalid month: $month. Month should be between 1 and 12." }

        val firstDay = LocalDateTime(year, Month(month), 1, 0, 0)

        val lastDayOfMonth = when (Month(month)) {
            Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
            else -> {
                if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            }
        }

        val lastDay = LocalDateTime(year, Month(month), lastDayOfMonth, 23, 59, 59)

        return "$month.${firstDay.dayOfMonth} ~ $month.${lastDay.dayOfMonth}"
    }
}

fun main() {
    // Test your code with different scenarios
    val startDateTime = LocalDateTime(2023, Month.JANUARY, 1, 0, 0)
    val endDateTime = LocalDateTime(2023, Month.JANUARY, 31, 23, 59)
    val daysInWeek = 7

    val result = getWeeksInMonth(startDateTime, endDateTime, daysInWeek)

    // Print the result for observation
    result.forEachIndexed { index, week ->
        println("Week ${index + 1}: $week")
    }
}
