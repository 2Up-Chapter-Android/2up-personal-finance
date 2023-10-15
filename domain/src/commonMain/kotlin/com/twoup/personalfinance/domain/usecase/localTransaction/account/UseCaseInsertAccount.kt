package com.twoup.personalfinance.domain.usecase.localTransaction.account

import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseInsertAccount(private val dataSource: TransactionLocalDataSource) {

    @OptIn(DelicateCoroutinesApi::class)
    fun insertAccount(account: AccountLocalModel) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                dataSource.insertAccount(account)
            }
        }
    }
}