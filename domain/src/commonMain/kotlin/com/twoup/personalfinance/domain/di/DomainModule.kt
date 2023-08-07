package com.twoup.personalfinance.domain.di

import com.twoup.personalfinance.domain.usecase.authentication.ActiveUserUseCase
import com.twoup.personalfinance.domain.usecase.authentication.LoginUseCase
import com.twoup.personalfinance.domain.usecase.authentication.RegisterUseCase
import com.twoup.personalfinance.domain.usecase.authentication.SendOtpUseCase
import com.twoup.personalfinance.domain.usecase.transaction.CreateTransactionUseCase
import com.twoup.personalfinance.domain.usecase.transaction.GetListWalletsUseCase
import org.koin.dsl.module

fun domainModule() = module {
    includes(
        authenticationDomainModule(),
        transactionDomainModule()
    )
}

private fun authenticationDomainModule() = module {
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { SendOtpUseCase(get()) }
    factory { ActiveUserUseCase(get()) }
}

private fun transactionDomainModule() = module {
    single { GetListWalletsUseCase(get()) }
    factory { CreateTransactionUseCase(get()) }
}