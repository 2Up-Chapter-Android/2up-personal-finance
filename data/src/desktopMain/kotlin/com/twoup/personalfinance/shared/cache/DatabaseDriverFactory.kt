package com.aibles.finance2upkmm.shared.cache

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import com.twoup.personalfinance.database.PersonalFinanceDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also {
            PersonalFinanceDatabase.Schema.create(it)
        }
        return driver
    }

}