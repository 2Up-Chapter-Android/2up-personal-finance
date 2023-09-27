package com.twoup.personalfinance.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel

sealed class MainScreenSharedScreen : ScreenProvider {
    object MainScreen : MainScreenSharedScreen()
    // daily screen
    object DailyScreen : MainScreenSharedScreen()
    data class ItemTransaction(val transaction : TransactionLocalModel) : MainScreenSharedScreen()
    object CalendarScreen : MainScreenSharedScreen()
    object MonthlyScreen : MainScreenSharedScreen()
    object TotalScreen : MainScreenSharedScreen()
    object NoteScreen : MainScreenSharedScreen()

}
