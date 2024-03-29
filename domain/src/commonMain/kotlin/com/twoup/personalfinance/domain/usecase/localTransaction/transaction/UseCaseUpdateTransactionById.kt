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
//                        transaction_id = transaction.transaction_id,
//                        transaction_income = transaction.transaction_income,
//                        transaction_expenses = transaction.transaction_expenses,
//                        transaction_transfer = transaction.transaction_transfer,
//                        transaction_description = transaction.transaction_description,
//                        transaction_note = transaction.transaction_note,
//                        transaction_created = transaction.transaction_created,
//                        transaction_month = transaction.transaction_month,
//                        transaction_year = transaction.transaction_year,
//                        transaction_category = transaction.transaction_category,
//                        transaction_account = transaction.transaction_account,
//                        transaction_selectIndex = transaction.transaction_selectIndex,
//                        transaction_accountTo = transaction.transaction_accountTo,
//                        transaction_accountFrom = transaction.transaction_accountFrom,

                        transactionAccount = transaction.transactionAccount,
                        transactionAccountFrom = transaction.transactionAccountFrom,
                        transactionAccountTo = transaction.transactionAccountTo,
                        transactionCategory = transaction.transactionCategory,
                        transactionCreated = transaction.transactionCreated,
                        transactionDescription = transaction.transactionDescription,
                        transactionExpenses = transaction.transactionId,
                        transactionId = transaction.transactionId,
                        transactionIncome = transaction.transactionIncome,
                        transactionMonth = transaction.transactionMonth,
                        transactionNote = transaction.transactionNote,
                        transactionSelectIndex = transaction.transactionSelectIndex,
                        transactionTransfer = transaction.transactionTransfer,
                        transactionYear = transaction.transactionYear
                    )
                )
            }
            loadTransaction
        }
    }
}