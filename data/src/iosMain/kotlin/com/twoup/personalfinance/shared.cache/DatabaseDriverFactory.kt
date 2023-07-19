package com.aibles.finance2upkmm.shared.cache

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.twoup.personalfinance.database.PersonalFinanceDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(PersonalFinanceDatabase.Schema, "test.db")
    }
}