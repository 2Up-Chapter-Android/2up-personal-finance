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
    val transactionAccountTo : String,
//    val transaction_id: Long,
//    val transaction_income: Long,
//    val transaction_expenses : Long,
//    val transaction_transfer : Long,
//    val transaction_description: String,
//    val transaction_note : String,
//    val transaction_created: LocalDateTime,
//    val transaction_month: Long,
//    val transaction_year: Long,
//    val transaction_category : String,
//    val transaction_account : String,
//    val transaction_selectIndex : Int,
//    val transaction_accountFrom : String,
//    val transaction_accountTo : String
)
