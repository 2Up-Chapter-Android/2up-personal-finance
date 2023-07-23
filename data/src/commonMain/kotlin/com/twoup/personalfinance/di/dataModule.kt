package com.twoup.personalfinance.di

import com.twoup.personalfinance.domain.repository.authentication.AuthenticationRepository
import com.twoup.personalfinance.domain.repository.transaction.TransactionRepository
import com.twoup.personalfinance.local.di.databaseModule
import com.twoup.personalfinance.local.di.localModule
import com.twoup.personalfinance.remote.di.networkModule
import com.twoup.personalfinance.remote.di.setupKtorfit
import com.twoup.personalfinance.repository.AuthenticationRepositoryImpl
import com.twoup.personalfinance.repository.TransactionRepositoryImpl
import org.koin.dsl.module

fun dataModule(baseUrl: String, enableNetworkLogs: Boolean) = module {
    includes(
        localModule(),
        databaseModule(),
        setupKtorfit(baseUrl, enableNetworkLogs),
        networkModule(),
        provideRepositories()
    )
}

private fun provideRepositories() = module {
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }
    single<TransactionRepository> { TransactionRepositoryImpl(get(), get()) }
}