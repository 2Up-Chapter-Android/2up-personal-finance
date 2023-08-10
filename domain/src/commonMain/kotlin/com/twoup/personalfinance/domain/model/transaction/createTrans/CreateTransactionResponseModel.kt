package com.twoup.personalfinance.domain.model.transaction.createTrans

import com.twoup.personalfinance.const.ConstDefaultValue
data class CreateTransactionResponseModel(
    val data: CreateTransactionResponseData = CreateTransactionResponseData(),
    val status: Int = 0,
    val statusMessage: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val timestamp: String = ConstDefaultValue.DEFAULT_VALUE_STRING
) {
    data class CreateTransactionResponseData(
        val amount: Int = 0,
        val categoryId: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
        val createdAt: Long = 0,
        val description: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
        val id: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
        val note: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
        val type: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
        val updatedAt: Long = 0,
        val walletId: String = ConstDefaultValue.DEFAULT_VALUE_STRING
    )
}