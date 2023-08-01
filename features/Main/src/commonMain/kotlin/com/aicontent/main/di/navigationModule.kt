package com.aicontent.main.di

import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.aicontent.main.presentation.MainScreen
import com.aicontent.main.presentation.calendar.CalendarScreen
import com.aicontent.main.presentation.daily.DailyScreen
import com.aicontent.main.presentation.monthly.MonthlyScreen
import com.aicontent.main.presentation.note.NoteScreen
import com.aicontent.main.presentation.total.TotalScreen
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import org.koin.dsl.module

fun mainScreenNavigationModule() = module {
    ScreenRegistry.register<MainScreenSharedScreen.MainScreen> {
        MainScreen()
    }
    ScreenRegistry.register<MainScreenSharedScreen.DailyScreen> {
        DailyScreen()
    }
    ScreenRegistry.register<MainScreenSharedScreen.CalendarScreen> {
        CalendarScreen()
    }
    ScreenRegistry.register<MainScreenSharedScreen.MonthlyScreen> {
        MonthlyScreen()
    }
    ScreenRegistry.register<MainScreenSharedScreen.TotalScreen> {
        TotalScreen()
    }
    ScreenRegistry.register<MainScreenSharedScreen.NoteScreen> {
        NoteScreen()
    }
}
