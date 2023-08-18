package com.aicontent.accounts.di

import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.aicontent.accounts.presentation.AccountListScreen
import com.twoup.personalfinance.navigation.AccountSharedScreen
import org.koin.dsl.module


fun accountScreenNavigationModule() = module {
    ScreenRegistry.register<AccountSharedScreen.AccountScreen> {
        AccountListScreen()
    }
}
