package com.twoup.personalfinance.domain.usecase.localTransaction

import com.twoup.personalfinance.domain.model.transaction.Transaction
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseFilterTransactionByMonth(private val dataSource: TransactionLocalDataSource) {

    val listTransactionState: MutableStateFlow<List<TransactionLocalModel>> =
        MutableStateFlow(listOf())

    @OptIn(DelicateCoroutinesApi::class)
    fun filterTransactionByMonth(monthNumber: Number, yearNumber: Number) {
        GlobalScope.launch {
            val transaction = withContext(Dispatchers.Default) {
                dataSource.getAllTransaction()
            }
            listTransactionState.value = transaction
                .filter { it.transaction_created.monthNumber == monthNumber && it.transaction_created.year == yearNumber }
        }
    }
}