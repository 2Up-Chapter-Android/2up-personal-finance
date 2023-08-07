package com.twoup.personalfinance.transaction.presentation.createTransaction

import com.twoup.personalfinance.domain.model.wallet.Wallet

data class CreateTransUiState(
    val isLoading: Boolean = false,

    val dateError: String = "",
    val amountError: String = "",
    val categoryError: String = "",
    val accountError: String = "",
    val noteError: String = "",

    val date: Long = 0,
    val amount: Int = 0,
    val category: String = "",
    val account: Wallet? = null,
    val note: String = "",
    val description: String = "",
    val isOpenChooseWallet: Boolean = false,

    val typeTrans: String = "EXPENSE"
) {
    val enableCreateTransButton
        get() = !isLoading &&
                date.toString().isNotBlank() &&
                amount.toString().isNotBlank()
//                category.isNotBlank()
//                note.isNotBlank()
}