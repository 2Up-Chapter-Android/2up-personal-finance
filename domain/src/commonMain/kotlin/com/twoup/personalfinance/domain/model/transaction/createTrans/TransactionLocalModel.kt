package com.twoup.personalfinance.domain.model.transaction.createTrans

import kotlinx.datetime.LocalDateTime

data class TransactionLocalModel(
    val transactionId: Long,
    val transactionIncome: Long,
    val transactionExpenses : Long,
    val transactionTransfer : Long,
    val transactionDescription: String,
    val transactionNote : String,
    val transactionCreated: LocalDateTime,
    val transactionMonth: Long,
    val transactionYear: Long,
    val transactionCategory : String,
    val transactionAccount : String,
    val transactionSelectIndex : Int,
    val transactionAccountFrom : String,
    val transactionAccountTo : String
)
