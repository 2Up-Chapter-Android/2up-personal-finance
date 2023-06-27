package com.twoup.personalfinance.remote.di

import com.twoup.personalfinance.remote.service.authentication.AuthenticationDataSource
import com.twoup.personalfinance.remote.service.authentication.AuthenticationService
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

fun networkModule() = module {
    single<AuthenticationService> { get<Ktorfit>().create() }
    single { AuthenticationDataSource(get()) }
}