package com.twoup.personalfinance.local.di

import org.koin.core.module.Module
import org.koin.dsl.module

fun localModule() = module {
    includes(secureStorageModule())
    //TODO: Local Data source
}

expect fun secureStorageModule(): Module