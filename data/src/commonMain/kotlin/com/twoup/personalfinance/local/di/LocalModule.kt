package com.twoup.personalfinance.local.di

import com.twoup.personalfinance.local.Database
import org.koin.core.module.Module
import org.koin.dsl.module

fun localModule() = module {
    includes(secureStorageModule())
    //TODO: Local Data source
    single { Database(get()) }
}

expect fun secureStorageModule(): Module