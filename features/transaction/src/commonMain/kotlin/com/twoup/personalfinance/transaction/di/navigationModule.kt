package com.twoup.personalfinance.transaction.di

import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import com.twoup.personalfinance.navigation.TransactionSharedScreen
import com.twoup.personalfinance.transaction.presentation.createTransaction.CreateTransactionScreen
import com.twoup.personalfinance.transaction.presentation.dashboard.TransactionDashboardScreen
import org.koin.dsl.module

fun transactionNavigationModule() = module {
    ScreenRegistry.register<TransactionSharedScreen.CreateTransactionScreen> {
        CreateTransactionScreen()
    }

    ScreenRegistry.register<TransactionSharedScreen.TransactionDashboardScreen> {
        TransactionDashboardScreen()
    }
//    ScreenRegistry.register<MainScreenSharedScreen.MainView> {
//        MainScreen()
//    }
}