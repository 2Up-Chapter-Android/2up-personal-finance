package com.twoup.personalfinance.domain.di

import com.twoup.personalfinance.domain.usecase.authentication.ActiveUserUseCase
import com.twoup.personalfinance.domain.usecase.authentication.LoginUseCase
import com.twoup.personalfinance.domain.usecase.authentication.RegisterUseCase
import com.twoup.personalfinance.domain.usecase.authentication.SendOtpUseCase
import com.twoup.personalfinance.domain.usecase.category.CategoryUseCase
import com.twoup.personalfinance.domain.usecase.transaction.GetListTransactionUseCase
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseDeleteAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseDeleteCategoryExpenseById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseDeleteCategoryIncomeById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseDeleteTransactionById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllCategoryExpenses
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllCategoryIncome
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseInsertAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseInsertCategoryIncome
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseInsertTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseUpdateAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseUpdateCategoryExpensesById
import com.twoup.personalfinance.domain.usecase.localTransaction.UseCaseUpdateCategoryIncomeById
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
    single { GetListTransactionUseCase(get()) }
}

private fun categoryDomainModule() = module {
    factory { CategoryUseCase(get()) }
//    single { CategoryUseCase(get()) }
}

private fun localTransactionDomainModule() = module {
    single { UseCaseDeleteAccountById(get()) }
    single { UseCaseDeleteCategoryExpenseById(get()) }
    single { UseCaseDeleteTransactionById(get()) }
    single { UseCaseDeleteCategoryIncomeById (get()) }
    single { UseCaseGetAllTransaction(get()) }
    single { UseCaseGetAllAccount(get()) }
    single { UseCaseGetAllCategoryExpenses(get()) }
    single { UseCaseGetAllCategoryIncome(get()) }
    single { UseCaseInsertAccount(get()) }
    single { UseCaseInsertTransaction(get()) }
    single { UseCaseInsertCategoryIncome(get()) }
    single { UseCaseGetAllCategoryExpenses(get()) }
    single { UseCaseUpdateAccountById(get()) }
    single { UseCaseUpdateTransactionById(get()) }
    single { UseCaseUpdateCategoryIncomeById(get()) }
    single { UseCaseUpdateCategoryExpensesById(get()) }
}