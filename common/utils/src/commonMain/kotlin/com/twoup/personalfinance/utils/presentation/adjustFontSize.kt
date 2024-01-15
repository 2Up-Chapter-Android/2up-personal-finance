package com.twoup.personalfinance.utils.presentation

fun adjustFontSize(text: String): Float {
    val maxLength = 10 // Maximum character length before font size decrease
    val fontSize10sp = 10f
    val fontSize12sp = 12f
    val fontSize14sp = 14f
    val fontSize16sp = 16f

    return when {
        text.length <= maxLength -> fontSize14sp
        text.length <= maxLength * 2 -> fontSize12sp
        text.length <= maxLength * 3 -> fontSize10sp
        else -> fontSize16sp
    }
}

fun getAbbreviatedMonth(monthNumber: Int): String {
    return when (monthNumber) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "Apr"
        5 -> "May"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Aug"
        9 -> "Sep"
        10 -> "Oct"
        11 -> "Nov"
        12 -> "Dec"
        else -> "Invalid Month"
    }
}