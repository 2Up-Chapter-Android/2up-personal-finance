package com.aibles.finance2upkmm.shared.cache

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.twoup.personalfinance.database.PersonalFinanceDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(PersonalFinanceDatabase.Schema, context, "test.db")
    }
}