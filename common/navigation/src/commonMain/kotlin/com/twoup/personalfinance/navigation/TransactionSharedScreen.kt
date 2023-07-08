package com.twoup.personalfinance.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class TransactionSharedScreen : ScreenProvider{
    object CreateTransactionScreen : TransactionSharedScreen()
}
