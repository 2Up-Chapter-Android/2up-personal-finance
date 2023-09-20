package com.twoup.personalfinance.transaction.presentation.createTransaction

import com.twoup.personalfinance.utils.DateTimeUtil
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

data class CreateTransUiState(
    val id: Long = 0L,
    val date: LocalDateTime = Instant.fromEpochMilliseconds(DateTimeUtil.toEpochMillis(DateTimeUtil.now()))
        .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
    val income: Long = 0,
    val expenses: Long = 0,
    val transferBalance : Long = 0,
    var category: String = "",
    var account: String = "",
    var accountFrom : String =  "",
    var accountTo : String = "",
    var description: String = "",
    var expensesAmount : Int = 0,
    val note: String = "",
    val isOpenDatePicker: Boolean = false,
    val isOpenChooseWallet: Boolean = false,
    val isOpenChooseCategory: Boolean = false,
    val isOpenChooseAccountTo: Boolean = false,
    val isOpenChooseAccountFrom: Boolean = false,

    val tabColor: Int = 0,

//    var account_id: Long = 0,
//    var account_name: String = "",
//    var account_type: String = "",
//    var descriptionAccount: String = "",
//    var income: Long = 0,
//    var expense: Long = 0,
)