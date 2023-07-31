package com.twoup.personalfinance.domain.model.wallet

import com.twoup.personalfinance.const.ConstDefaultValue

data class Wallet(
    val amount: Int = 0,
    val description: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val id: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val name: String = "",
    val walletGroup: WalletGroup = WalletGroup.OTHER
)

data class Category(
    val id: String? = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val name: String = "",
    val categoryId: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val userID: String = ConstDefaultValue.DEFAULT_VALUE_STRING
)