package com.twoup.personalfinance.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class TransactionSharedScreen : ScreenProvider{
    data class CreateTransactionScreen(val id: Long) : TransactionSharedScreen()
    object TransactionDashboardScreen : TransactionSharedScreen()
    object MainView: TransactionSharedScreen()
}
