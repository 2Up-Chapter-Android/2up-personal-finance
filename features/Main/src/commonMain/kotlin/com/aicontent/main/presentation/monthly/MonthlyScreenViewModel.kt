package com.aicontent.main.presentation.monthly

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseGetAllTransaction
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MonthlyScreenViewModel : ScreenModel, KoinComponent {

    private val useCaseGetAllTransaction: UseCaseGetAllTransaction by inject()

    init {
        loadTransaction()
    }
    fun loadTransaction() {
        useCaseGetAllTransaction.getAllTransaction()
    }


    var selectedTabIndex: MutableState<Int> = mutableStateOf(2)

    fun calculateTotalIncome(transactions: List<TransactionLocalModel>): Long {
        return transactions.filter { it.transactionIncome > 0 }.sumOf { it.transactionIncome }
    }

    fun calculateTotalExpenses(transactions: List<TransactionLocalModel>): Long {
        return transactions.filter { it.transactionExpenses > 0 }.sumOf { it.transactionExpenses }
    }

    fun calculateTotalLast(transactions: List<TransactionLocalModel>):Long{
        return calculateTotalIncome(transactions) - calculateTotalExpenses(transactions)
    }
    fun calculateColorText(transaction: TransactionLocalModel): Color {
        return when {
            transaction.transactionIncome - transaction.transactionExpenses > 0 -> Color.Blue
            transaction.transactionExpenses - transaction.transactionIncome > 0 -> Color.Red
            else -> Color.Black
        }
    }

    fun calculateIncomeOrExpenses(transaction: TransactionLocalModel): Long {
        return when {
            transaction.transactionIncome - transaction.transactionExpenses > 0 -> transaction.transactionIncome
            transaction.transactionExpenses - transaction.transactionIncome > 0 -> transaction.transactionExpenses
            else -> 0
        }
    }
}