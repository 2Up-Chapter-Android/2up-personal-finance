package com.twoup.personalfinance.transaction.presentation.createTransaction

import com.twoup.personalfinance.domain.model.wallet.Wallet

data class CreateTransUiState(
    val date: String = "",
    val amount: String = "",
    val category: String = "",
    val account: Wallet? = null,
    val note: String = "",
    val isOpenChooseWallet: Boolean = false,
    val tabColor: Int = 0
) {
}