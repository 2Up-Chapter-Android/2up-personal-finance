package com.twoup.personalfinance.domain.model.transaction.createTrans

import kotlinx.datetime.LocalDateTime

data class TransactionLocalModel(
    val transaction_id: Long?,
    val transaction_income: Long,
    val transaction_expenses : Long,
    val transaction_transfer : Long,
    val transaction_description: String,
    val transaction_note : String,
    val transaction_created: LocalDateTime,
    val transaction_category : String,
    val transaction_account : String,
    val transaction_selectIndex : Int,
    val transaction_accountFrom : String,
    val transaction_accountTo : String
)
