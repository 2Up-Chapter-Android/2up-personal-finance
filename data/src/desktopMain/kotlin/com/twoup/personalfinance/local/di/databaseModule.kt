package com.twoup.personalfinance.local.di

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import com.twoup.personalfinance.database.PersonalFinanceDatabase
import com.twoup.personalfinance.local.PersonalFinanceDatabaseWrapper
import org.koin.dsl.module

actual fun databaseModule() = module {
    single {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also { PersonalFinanceDatabase.Schema.create(it) }
        PersonalFinanceDatabaseWrapper(PersonalFinanceDatabase(driver))
    }
}