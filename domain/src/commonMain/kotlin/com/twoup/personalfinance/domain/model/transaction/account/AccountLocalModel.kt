package com.twoup.personalfinance.domain.model.transaction.account

data class AccountLocalModel(
    val account_id: Long?,
    val account_name: String,
    val account_type: String,
    val account_description: String,
    val account_asset: Long?,
    val account_liabilities: Long?,
)
