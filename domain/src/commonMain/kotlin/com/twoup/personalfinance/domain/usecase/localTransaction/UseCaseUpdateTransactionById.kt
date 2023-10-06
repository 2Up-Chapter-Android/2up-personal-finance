package com.twoup.personalfinance.domain.usecase.localTransaction

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
                        transaction_id = transaction.transaction_id,
                        transaction_income = transaction.transaction_income,
                        transaction_expenses = transaction.transaction_expenses,
                        transaction_transfer = transaction.transaction_transfer,
                        transaction_description = transaction.transaction_description,
                        transaction_note = transaction.transaction_note,
                        transaction_created = transaction.transaction_created,
                        transaction_category = transaction.transaction_category,
                        transaction_account = transaction.transaction_account,
                        transaction_selectIndex = transaction.transaction_selectIndex,
                        transaction_accountTo = transaction.transaction_accountTo,
                        transaction_accountFrom = transaction.transaction_accountFrom
                    )
                )
            }
            loadTransaction
        }
    }
}