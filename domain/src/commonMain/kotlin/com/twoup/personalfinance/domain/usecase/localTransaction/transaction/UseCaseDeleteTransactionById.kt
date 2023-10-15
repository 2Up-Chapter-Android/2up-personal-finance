package com.twoup.personalfinance.domain.usecase.localTransaction.transaction

import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseDeleteTransactionById(private val dataSource: TransactionLocalDataSource) {

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteTransactionById(id: Long, loadNote: Unit) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                dataSource.deleteTransactionById(id)
                loadNote
            }
        }
    }
}