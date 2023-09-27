package com.aicontent.main.presentation.daily

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.utils.DateTimeUtil
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

data class TransUiState(
    val date: LocalDateTime = Instant.fromEpochMilliseconds(DateTimeUtil.toEpochMillis(DateTimeUtil.now()))
        .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
    val amount: String = "",
    val category: String = "",
    val account: String = "",
    val note: String = "",
    val isOpenDatePicker: Boolean = false,
    val isOpenChooseWallet: Boolean = false,
    val isOpenChooseCategory: Boolean = false,
    val isOpenChooseAmount: Boolean = false,
    val isOpenChooseAccountFrom: Boolean = false,
    val isOpenChooseAccountTo: Boolean = false,
    val tabColor: Int = 0,
    val income : Long = 0,
    val expenses : Long = 0,
    val transfer : Long = 0,
    val accountFrom : String = "",
    val accountTo : String = ""

) {
}