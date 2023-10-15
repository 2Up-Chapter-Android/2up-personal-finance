package com.twoup.personalfinance.domain.usecase.localTransaction.account

import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseGetAccountById(private val dataSource: TransactionLocalDataSource) {

    @OptIn(DelicateCoroutinesApi::class)
    fun getAccountById(id: Long) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                dataSource.getAccountById(id)
            }
        }
    }
}