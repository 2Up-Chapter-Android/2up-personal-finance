package com.twoup.personalfinance.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider
import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel

sealed class AccountSharedScreen : ScreenProvider {
    object AccountScreen : AccountSharedScreen()
    data class ListTransactionForAccountScreen(val listTransaction : List<TransactionLocalModel>, val account: AccountLocalModel) : AccountSharedScreen()

}
