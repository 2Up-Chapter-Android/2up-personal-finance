package com.twoup.personalfinance.transaction.presentation.createTransaction

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
    val account: String = "",
    val note: String = "",

    val typeTrans: String = "EXPENSE"
) {
    val enableCreateTransButton
        get() = !isLoading &&
                date.toString().isNotBlank() &&
                amount.toString().isNotBlank() &&
                category.isNotBlank() &&
                account.isNotBlank() &&
                note.isNotBlank()
}