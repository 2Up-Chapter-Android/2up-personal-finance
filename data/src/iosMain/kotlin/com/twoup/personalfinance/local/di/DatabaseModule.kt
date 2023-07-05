package com.twoup.personalfinance.local.di

import com.twoup.personalfinance.database.PersonalFinanceDatabase
import com.twoup.personalfinance.local.PersonalFinanceDatabaseWrapper
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.dsl.module

actual fun databaseModule() = module {
    single {
        val driver = NativeSqliteDriver(PersonalFinanceDatabase.Schema, "personalfinance.db")
        PersonalFinanceDatabaseWrapper(PersonalFinanceDatabase(driver))
    }
}