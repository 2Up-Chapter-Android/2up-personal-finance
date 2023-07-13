package com.twoup.personalfinance.domain.model.wallet.getWallet

import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.wallet.Wallet
import com.twoup.personalfinance.domain.model.wallet.WalletGroup

data class GetListWalletResponseModel(
    val data: List<Wallet> = listOf(),
    val status: Int = 0,
    val statusMessage: String = ConstDefaultValue.DEFAULT_VALUE_STRING,
    val timestamp: String = ConstDefaultValue.DEFAULT_VALUE_STRING
)
