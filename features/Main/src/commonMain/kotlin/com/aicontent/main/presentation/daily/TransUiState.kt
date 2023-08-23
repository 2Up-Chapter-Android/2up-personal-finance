package com.aicontent.main.presentation.daily

import com.twoup.personalfinance.domain.model.wallet.Wallet

data class TransUiState(
    val date: String = "",
    val amount: String = "",
    val category: String = "",
    val account: Wallet = Wallet(),
    val note: String = "",
    val isOpenChooseWallet: Boolean = false,
    val tabColor: Int = 0
) {
}