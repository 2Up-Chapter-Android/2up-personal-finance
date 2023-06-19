package com.twoup.personalfinance.local.di

import com.twoup.personalfinance.database.PersonalFinanceDatabase
import com.twoup.personalfinance.local.PersonalFinanceDatabaseWrapper
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.dsl.module

actual fun databaseModule() = module {
    single {
        val driver = AndroidSqliteDriver(PersonalFinanceDatabase.Schema, get(), "personalfinance.db")
        PersonalFinanceDatabaseWrapper(PersonalFinanceDatabase(driver))
    }
}