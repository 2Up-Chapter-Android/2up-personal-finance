package com.twoup.personalfinance.domain.usecase.localTransaction

import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseUpdateCategoryById(private val dataSource: TransactionLocalDataSource) {

    @OptIn(DelicateCoroutinesApi::class)
    fun updateCategory(category: CategoryLocalModel, loadNote: Unit) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                dataSource.updateCategory(
                    CategoryLocalModel(
                        category_id = category.category_id,
                        category_name = category.category_name,
                        )
                )
            }
            loadNote
        }
    }
}