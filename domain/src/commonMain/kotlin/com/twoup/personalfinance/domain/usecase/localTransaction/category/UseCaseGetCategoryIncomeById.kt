package com.twoup.personalfinance.domain.usecase.localTransaction.category

import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseGetCategoryIncomeById(private val dataSource: TransactionLocalDataSource) {

    @OptIn(DelicateCoroutinesApi::class)
    fun getCategoryIncomeById(id: Long) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                dataSource.getCategoryIncomeById(id)
            }
        }
    }
}