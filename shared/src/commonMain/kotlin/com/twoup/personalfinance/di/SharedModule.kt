package com.twoup.personalfinance.di

import com.twoup.personalfinance.domain.di.domainModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(enableNetworkLogs: Boolean = true, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            dataModule("https://2up-finance-service.site/api/v1/", enableNetworkLogs),
            domainModule(),
//            sharedModule,
        )
    }

// called by iOS etc
// fun initKoin() = initKoin(enableNetworkLogs = false) {}

//val sharedModule = module {
//
//}