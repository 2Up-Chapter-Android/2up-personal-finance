package com.twoup.personalfinance.domain.model.transaction.createTrans

import kotlinx.datetime.LocalDateTime

data class TransactionLocalModel(
    val transaction_id: Long?,
    val amount: Double,
    val description: String,
    val created: LocalDateTime
)
