package com.aicontent.main.di

import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.aicontent.main.presentation.MainScreen
import com.aicontent.main.presentation.daily.ItemTransactionScreen
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import org.koin.dsl.module

fun mainScreenNavigationModule() = module {
    ScreenRegistry.register<MainScreenSharedScreen.MainScreen> {
        MainScreen()
    }
    ScreenRegistry.register<MainScreenSharedScreen.ItemTransaction> { provide ->
        ItemTransactionScreen(provide.transaction)
    }
}
