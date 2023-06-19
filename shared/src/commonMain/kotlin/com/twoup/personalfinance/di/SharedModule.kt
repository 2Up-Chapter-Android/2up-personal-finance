package com.twoup.personalfinance.di

import com.twoup.personalfinance.domain.di.domainModule
import com.twoup.personalfinance.features.people.di.featurePeopleModule
import com.twoup.personalfinance.local.di.databaseModule
import com.twoup.personalfinance.local.di.localModule
import com.twoup.personalfinance.remote.di.remoteModule
import com.twoup.personalfinance.viewmodel.ApplicationViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = true, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            databaseModule(),
            localModule(),
            remoteModule("https://swapi.dev/api/", enableNetworkLogs),
            domainModule(),
            sharedModule,
            featurePeopleModule,
        )
    }

// called by iOS etc
// fun initKoin() = initKoin(enableNetworkLogs = false) {}

val sharedModule = module {
    single {
        ApplicationViewModel(
            settingsFactory = get()
        )
    }
}

fun KoinApplication.Companion.start(): KoinApplication = initKoin { }