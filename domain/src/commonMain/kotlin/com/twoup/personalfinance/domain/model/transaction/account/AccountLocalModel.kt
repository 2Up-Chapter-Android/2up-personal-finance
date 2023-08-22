package com.twoup.personalfinance.domain.model.transaction.account

data class AccountLocalModel(
    val account_id: Long?,
    val account_name: String,
    val account_type: String,
    val description: String,
    val income: Double?,
    val expense: Double?,
)
