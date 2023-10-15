package com.twoup.personalfinance.domain.usecase.localTransaction.account

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
                        account_description = account.account_description,
                        account_asset  = account.account_asset,
                        account_liabilities = account.account_liabilities
                    )
                )
            }
            loadNote
        }
    }
}