package com.aicontent.status.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.usecase.localTransaction.transaction.UseCaseGetAllTransaction
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StatusViewModel: ScreenModel, KoinComponent {
    private val useCaseGetAllTransaction: UseCaseGetAllTransaction by inject()

    val transaction: StateFlow<List<TransactionLocalModel>> get() = useCaseGetAllTransaction.transactionState.asStateFlow()

    fun calculateTotalIncome(transactions: List<TransactionLocalModel>): Long {
        return transactions.filter { it.transactionIncome > 0 }.sumOf { it.transactionIncome }
    }

    fun calculateTotalExpenses(transactions: List<TransactionLocalModel>): Long {
        return transactions.filter { it.transactionExpenses > 0 }.sumOf { it.transactionExpenses }
    }
}