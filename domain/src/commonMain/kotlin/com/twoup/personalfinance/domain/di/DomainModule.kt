package com.twoup.personalfinance.domain.di

import com.twoup.personalfinance.domain.usecase.authentication.ActiveUserUseCase
import com.twoup.personalfinance.domain.usecase.authentication.LoginUseCase
import com.twoup.personalfinance.domain.usecase.authentication.RegisterUseCase
import com.twoup.personalfinance.domain.usecase.authentication.SendOtpUseCase
import com.twoup.personalfinance.domain.usecase.category.CategoryUseCase
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseDeleteAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseDeleteCategoryById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseDeleteTransactionById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllCategory
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseInsertAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseInsertCategory
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseInsertTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseUpdateAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseUpdateCategoryById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseUpdateTransactionById
import com.twoup.personalfinance.domain.usecase.transaction.GetListWalletsUseCase
import org.koin.dsl.module

fun domainModule() = module {
    includes(
        authenticationDomainModule(),
        transactionDomainModule(),
        categoryDomainModule(),
        localTransactionDomainModule()
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
}

private fun categoryDomainModule() = module {
    factory { CategoryUseCase(get()) }
//    single { CategoryUseCase(get()) }
}

private fun localTransactionDomainModule() = module {
//    single<TransactionLocalDataSource> { TransactionLocalDataSourceImpl(get()) }

    single { UseCaseDeleteAccountById(get()) }
    single { UseCaseDeleteCategoryById(get()) }
//    single { UseCaseDeleteSubCategoriesById(get()) }
    single { UseCaseDeleteTransactionById(get()) }
    single { UseCaseGetAllTransaction(get()) }
    single { UseCaseGetAllAccount(get()) }
    single { UseCaseGetAllCategory(get()) }
//    single { UseCaseGetAllSubCategories(get()) }
    single { UseCaseInsertAccount(get()) }
    single { UseCaseInsertCategory(get()) }
//    single { UseCaseInsertSubCategories(get()) }
    single { UseCaseInsertTransaction(get()) }
    single { UseCaseUpdateAccountById(get()) }
    single { UseCaseUpdateCategoryById(get()) }
//    single { UseCaseUpdateSubCategoriesById(get()) }
    single { UseCaseUpdateTransactionById(get()) }
}