package com.twoup.personalfinance.domain.model.transaction

import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.wallet.Category
import com.twoup.personalfinance.domain.model.wallet.Wallet


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

data class Transaction(
    val income: Int = 0,
    val expenses: Int = 0,
    val category: Category = Category(),
    val createdAt: Long = 0,
    val description: String = "",
    val id: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val note: String = "",
    val type: TransactionType = TransactionType.DEFAULT,
    val updatedAt: Long = 0,
    val wallet: Wallet = Wallet()
)