package com.twoup.personalfinance.di

import com.aicontent.category.di.categoryNavigationModule
import com.aicontent.main.di.mainScreenNavigationModule
import com.twoup.personalfinance.authentication.di.authenticationNavigationModule
import com.twoup.personalfinance.domain.di.domainModule
import com.twoup.personalfinance.transaction.di.transactionNavigationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(enableNetworkLogs: Boolean = true, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            dataModule("https://2up-finance-service.site/api/v1/", enableNetworkLogs),
            domainModule(),
            authenticationNavigationModule(),
            transactionNavigationModule(),
            categoryNavigationModule(),
            mainScreenNavigationModule()
        )
    }

// called by iOS etc
// fun initKoin() = initKoin(enableNetworkLogs = false) {}

//val sharedModule = module {
//
//}