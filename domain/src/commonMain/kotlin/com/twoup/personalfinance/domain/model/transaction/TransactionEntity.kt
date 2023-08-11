package com.twoup.personalfinance.domain.model.transaction

import com.twoup.personalfinance.const.ConstDefaultValue


data class TransactionEntity(
    val amount: Int = 0,
    val categoryId: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val createdAt: Long = 0,
    val description: String = "",
    val id: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val note: String = "",
    val type: TransactionType = TransactionType.DEFAULT,
    val updatedAt: Long = 0,
    val walletId: String = ConstDefaultValue.DEFAULT_VALUE_STRING
)