package com.aicontent.status.di

import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.aicontent.status.presentation.StatusScreen
import com.twoup.personalfinance.navigation.StatusScreenSharedScreen
import org.koin.dsl.module

fun statusNavigationModule() = module {
    ScreenRegistry.register<StatusScreenSharedScreen.StatusScreen> {
        StatusScreen()
    }
}
