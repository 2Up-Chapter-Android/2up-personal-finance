package com.twoup.personalfinance

import androidx.compose.ui.window.Application
import com.twoup.personalfinance.ui.MainComposeView

fun getRootController() = Application("MainComposeView") {
    MainComposeView()
}
