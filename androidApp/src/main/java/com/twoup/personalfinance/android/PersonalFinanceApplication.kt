package com.twoup.personalfinance.android

import android.app.Application
import com.twoup.personalfinance.di.initKoin
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class PersonalFinanceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(level = if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(androidContext = this@PersonalFinanceApplication)
        }
    }
}