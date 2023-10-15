package com.twoup.personalfinance.domain.usecase.localTransaction.transaction

import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseInsertTransaction(private val dataSource: TransactionLocalDataSource) {
    @OptIn(DelicateCoroutinesApi::class)
    fun insertTransaction(transaction: TransactionLocalModel, LoadTransaction : Unit) {
        GlobalScope.launch {
            dataSource.insertTransaction(transaction)
            LoadTransaction
        }
    }
}