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