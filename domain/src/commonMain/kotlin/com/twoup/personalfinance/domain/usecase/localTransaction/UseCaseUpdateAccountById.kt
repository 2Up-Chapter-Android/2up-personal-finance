package com.twoup.personalfinance.domain.usecase.localTransaction

import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseUpdateAccountById(private val dataSource: TransactionLocalDataSource) {

    @OptIn(DelicateCoroutinesApi::class)
    fun updateAccount(account: AccountLocalModel, loadNote: Unit) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                dataSource.updateAccount(
                    AccountLocalModel(
                        account_id = account.account_id,
                        account_name = account.account_name,
                        account_type = account.account_type,
                        description = account.description,
                        asset  = account.asset,
                        liabilities = account.liabilities
                    )
                )
            }
            loadNote
        }
    }
}