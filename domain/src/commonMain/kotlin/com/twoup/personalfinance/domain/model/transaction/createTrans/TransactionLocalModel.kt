package com.twoup.personalfinance.domain.model.transaction.createTrans

import kotlinx.datetime.LocalDateTime

data class TransactionLocalModel(
    val transaction_id: Long?,
    val income: Long,
    val expenses : Long,
    val transferBalance : Long,
    val description: String,
    val created: LocalDateTime,
    val category : String,
    val account : String,
    val selectIndex : Int,
    val accountFrom : String,
    val accountTo : String
)
