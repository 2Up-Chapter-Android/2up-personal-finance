package com.twoup.personalfinance.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class MainScreenSharedScreen : ScreenProvider {
    object MainScreen : MainScreenSharedScreen()
    object DailyScreen : MainScreenSharedScreen()
    object CalendarScreen : MainScreenSharedScreen()
    object MonthlyScreen : MainScreenSharedScreen()
    object TotalScreen : MainScreenSharedScreen()
    object NoteScreen : MainScreenSharedScreen()

}
