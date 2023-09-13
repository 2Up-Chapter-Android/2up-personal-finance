package com.twoup.personalfinance.transaction.presentation.createTransaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.utils.DateTimeUtil
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

data class CreateTransUiState(
    val id : Long = 0L,
    val date: LocalDateTime = Instant.fromEpochMilliseconds(DateTimeUtil.toEpochMillis(DateTimeUtil.now())).toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
    val amount: Double = 0.0,
    var category: String = "",
//    var textAccount: String = "",
    var account: String = "",
    var description : String = "",
//    val account: Wallet? = null,
    val note: String = "",
    val isOpenDatePicker: Boolean = false,
    val isOpenChooseWallet: Boolean = false,
    val isOpenChooseCategory: Boolean = false,
    val isOpenChooseAmount: Boolean = false,
    val tabColor: Int = 0
) {
}

//class CreateTransUiState(
//    id: Long = 0L,
//    amount: Double = 0.0,
//    note: String = "",
//    date: LocalDateTime = DateTimeUtil.now(),
//    account: String = "",
//    val isOpenChooseWallet: Boolean = false,
//    val isOpenChooseCategory: Boolean = false,
//    val isOpenChooseAmount: Boolean = false,
//    tabColor: Int = 0,
//
//) {
//    var id by mutableStateOf(id)
//    var amount by mutableStateOf(amount)
//    var note by mutableStateOf(note)
//    var date by mutableStateOf(date)
//    var account by mutableStateOf(account)
//    var tabColor by mutableStateOf(tabColor)
////    var trash by mutableStateOf(trash)
////    var tag by mutableStateOf(tag)
////    var folder by mutableStateOf(folder)
//}
