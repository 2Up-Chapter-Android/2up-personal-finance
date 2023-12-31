package com.twoup.personalfinance.domain.usecase.localTransaction.transaction

import com.twoup.personalfinance.domain.model.transaction.Transaction
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month

class UseCaseFilterTransactionByMonth(private val dataSource: TransactionLocalDataSource) {

    val listTransactionState: MutableStateFlow<List<TransactionLocalModel>> =
        MutableStateFlow(listOf())

    @OptIn(DelicateCoroutinesApi::class)
    fun filterTransactionByMonth(month: Long, year: Long) {
        GlobalScope.launch {
            val transaction = withContext(Dispatchers.Default) {
                dataSource.filterTransactionByMonth(month,year)
            }
            listTransactionState.value = transaction
        }
    }
}