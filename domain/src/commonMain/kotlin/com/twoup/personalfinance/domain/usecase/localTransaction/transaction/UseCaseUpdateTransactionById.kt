package com.twoup.personalfinance.domain.usecase.localTransaction.transaction

import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseUpdateTransactionById(private val dataSource: TransactionLocalDataSource) {
    @OptIn(DelicateCoroutinesApi::class)
    fun updateTransaction(transaction: TransactionLocalModel, loadTransaction: Unit) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                dataSource.updateTransaction(
                    TransactionLocalModel(
                        transactionId = transaction.transactionId,
                        transactionIncome = transaction.transactionIncome,
                        transactionExpenses = transaction.transactionExpenses,
                        transactionTransfer = transaction.transactionTransfer,
                        transactionDescription = transaction.transactionDescription,
                        transactionNote = transaction.transactionNote,
                        transactionCreated = transaction.transactionCreated,
                        transactionMonth = transaction.transactionMonth,
                        transactionYear = transaction.transactionYear,
                        transactionCategory = transaction.transactionCategory,
                        transactionAccount = transaction.transactionAccount,
                        transactionSelectIndex = transaction.transactionSelectIndex,
                        transactionAccountTo = transaction.transactionAccountTo,
                        transactionAccountFrom = transaction.transactionAccountFrom
                    )
                )
            }
            loadTransaction
        }
    }
}