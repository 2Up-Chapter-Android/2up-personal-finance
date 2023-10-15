package com.twoup.personalfinance.domain.usecase.localTransaction.transaction

import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseGetAllTransaction(private val dataSource: TransactionLocalDataSource) {

    val transactionState: MutableStateFlow<List<TransactionLocalModel>> =
        MutableStateFlow(listOf())
    @OptIn(DelicateCoroutinesApi::class)
    fun getAllTransaction() {
        GlobalScope.launch {
            val transaction = withContext(Dispatchers.Default) {
                dataSource.getAllTransaction()
            }
            transactionState.value = transaction
        }
    }
}