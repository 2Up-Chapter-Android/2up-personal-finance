package com.twoup.personalfinance.local.di

import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import com.twoup.personalfinance.local.Database
import com.twoup.personalfinance.local.IDatabase
import com.twoup.personalfinance.local.transaction.TransactionLocalDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

fun localModule() = module {
    includes(secureStorageModule())

    single<IDatabase> { Database(get()) }
    single<TransactionLocalDataSource> { TransactionLocalDataSourceImpl(get()) }
}

expect fun secureStorageModule(): Module