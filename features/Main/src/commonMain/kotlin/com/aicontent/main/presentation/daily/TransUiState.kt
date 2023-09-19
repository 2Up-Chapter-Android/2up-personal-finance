package com.aicontent.main.presentation.daily

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
//    val account: Wallet = Wallet(),
    val account: String = "",
    val note: String = "",
    val isOpenDatePicker: Boolean = false,
    val isOpenChooseWallet: Boolean = false,
    val isOpenChooseCategory: Boolean = false,
    val isOpenChooseAmount: Boolean = false,
    val tabColor: Int = 0,
) {

}