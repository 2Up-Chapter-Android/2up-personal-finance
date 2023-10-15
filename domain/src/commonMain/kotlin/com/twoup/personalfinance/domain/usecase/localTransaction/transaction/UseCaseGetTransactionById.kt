package com.twoup.personalfinance.domain.usecase.localTransaction.transaction

import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import com.twoup.personalfinance.utils.DateTimeUtil
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import kotlinx.coroutines.*

class UseCaseGetTransactionById(private val dataSource: TransactionLocalDataSource) {

    val transactionState: MutableStateFlow<TransactionLocalModel> =
        MutableStateFlow(
            TransactionLocalModel(
                transaction_id = 0,
                transaction_created = DateTimeUtil.now(),
                transaction_month = DateTimeUtil.now().month.ordinal.toLong(),
                transaction_transfer = 0,
                transaction_selectIndex = 0,
                transaction_note = "",
                transaction_category = "",
                transaction_accountTo = "",
                transaction_account = "",
                transaction_description = "",
                transaction_expenses = 0,
                transaction_income = 0,
                transaction_accountFrom = "",
                transaction_year = 0,
            )
        )

    @OptIn(DelicateCoroutinesApi::class)
    fun getTransactionById(id: Long) {
        GlobalScope.launch {
            val transaction = withContext(Dispatchers.Default) {
                dataSource.getTransactionById(id)
            }

            // Update the transactionState with the fields from the retrieved transaction
            transactionState.value = transaction
//            transactionState.value = transactionState.value.copy(
//                transaction_id = transaction.transaction_id,
//                transaction_income = transaction.transaction_income,
//                transaction_expenses = transaction.transaction_expenses,
//                transaction_transfer = transaction.transaction_transfer,
//                transaction_description = transaction.transaction_description,
//                transaction_note = transaction.transaction_note,
//                transaction_created = transaction.transaction_created,
//                transaction_month = transaction.transaction_month,
//                transaction_year = transaction.transaction_year,
//                transaction_category = transaction.transaction_category,
//                transaction_account = transaction.transaction_account,
//                transaction_selectIndex = transaction.transaction_selectIndex,
//                transaction_accountFrom = transaction.transaction_accountFrom,
//                transaction_accountTo = transaction.transaction_accountTo
//            )
        }
    }
}

