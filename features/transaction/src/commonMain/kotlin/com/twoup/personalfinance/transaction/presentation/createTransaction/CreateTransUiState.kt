package com.twoup.personalfinance.transaction.presentation.createTransaction

import com.twoup.personalfinance.domain.model.wallet.Wallet

data class CreateTransUiState(
    val date: String = "",
    val amount: String = "",
    var category: String = "",
    var textAccount: String = "",
    var account: String = "",
//    val account: Wallet? = null,
    val note: String = "",
    val isOpenChooseWallet: Boolean = false,
    val isOpenChooseCategory: Boolean = false,
    val isOpenChooseAmount: Boolean = false,
    val tabColor: Int = 0
) {
}