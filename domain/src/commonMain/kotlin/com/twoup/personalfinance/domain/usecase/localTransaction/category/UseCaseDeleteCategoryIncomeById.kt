package com.twoup.personalfinance.domain.usecase.localTransaction.category

import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseDeleteCategoryIncomeById(private val dataSource: TransactionLocalDataSource) {

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteCategoryIncomeById(id: Long, loadNote: Unit) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                dataSource.deleteCategoryIncomeById(id)
                loadNote
            }
        }
    }
}
