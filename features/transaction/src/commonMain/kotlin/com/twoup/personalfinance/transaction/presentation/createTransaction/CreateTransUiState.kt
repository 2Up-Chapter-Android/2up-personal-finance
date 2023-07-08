package com.twoup.personalfinance.transaction.presentation.createTransaction

data class CreateTransUiState(
    val date: String = "",
    val amount: String = "",
    val category: String = "",
    val account: String = "",
    val note: String = "",

    val tabColor: Int = 0
) {
}