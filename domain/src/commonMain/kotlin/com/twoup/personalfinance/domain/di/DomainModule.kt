package com.twoup.personalfinance.domain.di

import com.twoup.personalfinance.domain.usecase.authentication.ActiveUserUseCase
import com.twoup.personalfinance.domain.usecase.authentication.LoginUseCase
import com.twoup.personalfinance.domain.usecase.authentication.RegisterUseCase
import com.twoup.personalfinance.domain.usecase.authentication.SendOtpUseCase
import com.twoup.personalfinance.domain.usecase.category.CategoryUseCase
import com.twoup.personalfinance.domain.usecase.transaction.GetListTransactionUseCase
import com.twoup.personalfinance.domain.usecase.localTransaction.account.UseCaseDeleteAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.account.UseCaseGetAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.category.UseCaseDeleteCategoryExpenseById
import com.twoup.personalfinance.domain.usecase.localTransaction.category.UseCaseDeleteCategoryIncomeById
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseDeleteTransactionById
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseFilterTransactionByMonth
import com.twoup.personalfinance.domain.usecase.localTransaction.account.UseCaseGetAllAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.category.UseCaseGetAllCategoryExpenses
import com.twoup.personalfinance.domain.usecase.localTransaction.category.UseCaseGetAllCategoryIncome
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseGetAllTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.account.UseCaseInsertAccount
import com.twoup.personalfinance.domain.usecase.localTransaction.category.UseCaseInsertCategoryIncome
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseInsertTransaction
import com.twoup.personalfinance.domain.usecase.localTransaction.account.UseCaseUpdateAccountById
import com.twoup.personalfinance.domain.usecase.localTransaction.category.UseCaseUpdateCategoryExpensesById
import com.twoup.personalfinance.domain.usecase.localTransaction.category.UseCaseUpdateCategoryIncomeById
import com.twoup.personalfinance.domain.usecase.localTransaction.note.UseCaseGetNoteById
<<<<<<< HEAD
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseFilterTransactionByYear
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseGetTransactionById
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseSearchTransactionByNote
=======
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseGetTransactionById
>>>>>>> fd594fb534333d1d134a6821078b606b76c8c827
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseUpdateTransactionById
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
    single { UseCaseFilterTransactionByMonth(get()) }
<<<<<<< HEAD
    single { UseCaseFilterTransactionByYear(get()) }
=======
>>>>>>> fd594fb534333d1d134a6821078b606b76c8c827
    single { UseCaseGetAccountById(get())}
    single { UseCaseGetTransactionById(get())}
    single { UseCaseDeleteCategoryIncomeById(get())}
    single { UseCaseDeleteCategoryExpenseById(get())}
    single { UseCaseGetNoteById(get())}
<<<<<<< HEAD
    single { UseCaseSearchTransactionByNote(get())}
=======


>>>>>>> fd594fb534333d1d134a6821078b606b76c8c827
}