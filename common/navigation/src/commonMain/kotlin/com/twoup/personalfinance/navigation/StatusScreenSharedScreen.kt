package com.twoup.personalfinance.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class StatusScreenSharedScreen : ScreenProvider {
    object StatusScreen : StatusScreenSharedScreen()
}
