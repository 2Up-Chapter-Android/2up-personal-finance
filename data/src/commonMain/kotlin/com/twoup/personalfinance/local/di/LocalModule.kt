package com.twoup.personalfinance.local.di

import com.twoup.personalfinance.local.Database
import com.twoup.personalfinance.local.IDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

fun localModule() = module {
    includes(secureStorageModule())

    single<IDatabase> { Database(get()) }
}

expect fun secureStorageModule(): Module