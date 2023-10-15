package com.twoup.personalfinance.transaction.presentation.createTransaction

import com.twoup.personalfinance.utils.DateTimeUtil
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

data class CreateTransUiState(
    val id : Long = 0,
    val date: LocalDateTime = Instant.fromEpochMilliseconds(DateTimeUtil.toEpochMillis(DateTimeUtil.now()))
        .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
    val month : Long = 0,
    val year : Long = 0,
    val category: String = "",
    val account: String = "",
    val note: String = "",
    val description : String = "",
    val income : Long = 0,
    val expenses : Long = 0,
    val transfer : Long = 0,
    val accountFrom : String = "",
    val accountTo : String = "",
    val isOpenDatePicker: Boolean = false,
    val isOpenChooseWallet: Boolean = false,
    val isOpenChooseCategory: Boolean = false,
    val isOpenChooseAmount: Boolean = false,
    val isOpenChooseAccountFrom: Boolean = false,
    val isOpenChooseAccountTo: Boolean = false,
    val selectIndex: Int = 0,
    val showSaveButton : Boolean = false
)