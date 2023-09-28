package com.twoup.personalfinance.domain.usecase.localTransaction

import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseGetAllCategoryIncome(private val dataSource: TransactionLocalDataSource) {
    val categoryIncomeState: MutableStateFlow<List<CategoryLocalModel>> =
        MutableStateFlow(listOf())

    @OptIn(DelicateCoroutinesApi::class)
    fun getAllCategoryIncome() {
        GlobalScope.launch {
            val category = withContext(Dispatchers.Default) {
                dataSource.getAllCategoryIncome()
            }
            categoryIncomeState.value = category
        }
    }
}