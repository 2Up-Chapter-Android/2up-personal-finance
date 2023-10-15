package com.twoup.personalfinance.domain.usecase.localTransaction.category

import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseInsertCategoryExpenses(private val dataSource: TransactionLocalDataSource) {

    @OptIn(DelicateCoroutinesApi::class)
    fun insertCategoryExpenses(category: CategoryLocalModel) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                dataSource.insertCategoryExpenses(category)
            }
        }
    }
}