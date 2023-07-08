package com.twoup.personalfinance.remote.di

import com.twoup.personalfinance.remote.services.authentication.AuthenticationDataSource
import com.twoup.personalfinance.remote.services.authentication.AuthenticationService
import com.twoup.personalfinance.remote.services.transaction.TransactionDataSource
import com.twoup.personalfinance.remote.services.transaction.TransactionService
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

fun networkModule() = module {
    single<AuthenticationService> { get<Ktorfit>().create() }
    single { AuthenticationDataSource(get()) }

    single<TransactionService> { get<Ktorfit>().create() }
    single { TransactionDataSource(get()) }
}