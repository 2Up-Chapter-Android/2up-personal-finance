package com.twoup.personalfinance.domain.usecase.localTransaction.account

import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseGetAllAccount(private val dataSource: TransactionLocalDataSource) {

    val accountState: MutableStateFlow<List<AccountLocalModel>> = MutableStateFlow(listOf())

    @OptIn(DelicateCoroutinesApi::class)
    fun getAllAccount() {
        GlobalScope.launch {
            val account = withContext(Dispatchers.Default) {
                dataSource.getALlAccount()
            }
            accountState.value = account
        }
    }
}