package com.twoup.personalfinance.domain.di

import com.twoup.personalfinance.domain.repository.category.CategoryRepository
import com.twoup.personalfinance.domain.usecase.authentication.ActiveUserUseCase
import com.twoup.personalfinance.domain.usecase.authentication.LoginUseCase
import com.twoup.personalfinance.domain.usecase.authentication.RegisterUseCase
import com.twoup.personalfinance.domain.usecase.authentication.SendOtpUseCase
import com.twoup.personalfinance.domain.usecase.category.CategoryUseCase
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

}

private fun  categoryDomainModule() = module {
//    single<CategoryRepository> {CategoryRepositoryImpl(get(), get())}
    factory { CategoryUseCase(get()) }
}