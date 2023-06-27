package com.twoup.personalfinance.local.di

import com.twoup.personalfinance.local.SecureStorageWrapper
import com.twoup.personalfinance.local.SecureStorageWrapperImpl
import org.koin.dsl.module

actual fun secureStorageModule() = module {
    single<SecureStorageWrapper> { SecureStorageWrapperImpl() }
}