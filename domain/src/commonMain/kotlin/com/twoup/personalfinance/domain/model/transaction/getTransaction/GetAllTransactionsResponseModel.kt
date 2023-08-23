package com.twoup.personalfinance.domain.model.transaction.getTransaction


import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.transaction.TransactionEntity

data class GetAllTransactionsResponseModel(
    val data: List<TransactionEntity> = listOf(),
    val status: Int = 0,
    val statusMessage: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val timestamp: String = ConstDefaultValue.DEFAULT_VALUE_STRING
)