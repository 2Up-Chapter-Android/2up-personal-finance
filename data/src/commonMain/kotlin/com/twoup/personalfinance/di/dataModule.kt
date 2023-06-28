package com.twoup.personalfinance.di

import com.twoup.personalfinance.domain.repository.authentication.AuthenticationRepository
import com.twoup.personalfinance.local.di.databaseModule
import com.twoup.personalfinance.local.di.localModule
import com.twoup.personalfinance.remote.di.networkModule
import com.twoup.personalfinance.remote.di.remoteModule
import com.twoup.personalfinance.repository.AuthenticationRepositoryImpl
import org.koin.dsl.module

fun dataModule(baseUrl: String, enableNetworkLogs: Boolean) = module {
    includes(
        localModule(),
        databaseModule(),
        remoteModule(baseUrl, enableNetworkLogs),
        networkModule()
    )
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }
}